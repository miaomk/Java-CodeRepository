package com.techwells.wumei.controller;

import com.techwells.wumei.domain.*;
import com.techwells.wumei.domain.rs.RsShoppingCart;
import com.techwells.wumei.service.AddressService;
import com.techwells.wumei.service.ProductService;
import com.techwells.wumei.service.ShoppingCartService;
import com.techwells.wumei.service.StockService;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping(value = "*.do")
@RestController
public class ShoppingCartController {

	@Resource
	private ShoppingCartService shoppingCartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AddressService addressService;
	
	@Resource
	private StockService stockService;

	/**
	 * 添加购物车
	 *  加入商品到购物车
	 *  如果已经存在购物车商品，则增加数量；
	 *  否则添加新的购物车商品项。
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/shoppingCart/addShoppingCart")
	public @ResponseBody
	Object addShoppingCart(HttpServletRequest request, HttpSession session,
						   HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();
		String userId = request.getParameter("userId");
		String productId = request.getParameter("productId");
		String number = request.getParameter("number");
		String specification = request.getParameter("specification");

		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户未登录！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (productId == null || productId.equals("")) {
			rsInfo.setMessage("商品ID不能为空！");
			rsInfo.setCode("9998");
			return rsInfo;
		}
		if (specification == null) {
			rsInfo.setMessage("商品规格不能为空！");
			rsInfo.setCode("9998");
			return rsInfo;
		}
		
		if (number == null || "".equals(number)) {
			rsInfo.setMessage("商品数量不能为空！");
			rsInfo.setCode("9997");
			return rsInfo;
		}else if(Integer.parseInt(number)<=0){
			rsInfo.setMessage("商品数量不能小于0！");
			rsInfo.setCode("9996");
			return rsInfo;
		}
		
		
		Product product = productService.getProductByProductId(Integer
				.parseInt(productId));
		if (product == null) {
			rsInfo.setMessage("获取商品信息失败！");
			rsInfo.setCode("35314");
			return rsInfo;
		}

		// 判断库存
		
		Stock s = new Stock();
		s.setProductId(Integer.parseInt(productId));
		s.setSpecification(specification);
		
		Stock st = stockService.getStockByStock(s);
		
		if(st == null) {
			rsInfo.setMessage("商品" + product.getProductName() + "规格" + specification+ "不存在！");
			rsInfo.setCode("35314");
			return rsInfo;				
		}
		
		if(st != null && st.getStock() < Integer.parseInt(number)) {
			rsInfo.setMessage("商品" + product.getProductName() + "规格" + specification+ "库存不足！");
			rsInfo.setCode("35315");
			return rsInfo;					
		}
		

		int count = 0;
		try {
			//查找购物车中是否存在该商品
			ShoppingCartKey shoppingCartKey = new ShoppingCartKey();
			shoppingCartKey.setProductId(Integer.parseInt(productId));
			shoppingCartKey.setUserId(Integer.parseInt(userId));
			shoppingCartKey.setSpecification(specification);
			
			ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByShoppingCartId(shoppingCartKey);
			if(shoppingCart != null){
				shoppingCart.setNumber(shoppingCart.getNumber()+Integer.parseInt(number));
				count = shoppingCartService.modifyShoppingCart(shoppingCart);			
			}else{
				ShoppingCart sc = new ShoppingCart();
				sc.setCreatedDate(new Date());
				sc.setProductId(Integer.parseInt(productId));
				sc.setUserId(Integer.parseInt(userId));
				sc.setNumber(Integer.parseInt(number));
				sc.setSpecification(specification);
				
				count = shoppingCartService.addShoppingCart(sc);
			}
			//同时库存减的数量为number
			st.setStock(st.getStock() - Integer.parseInt(number));
			count = stockService.modifyStock(st);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加购物车异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加购物车成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加购物车失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改购物车信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/modifyShoppingCart")
	public @ResponseBody Object modifyShoppingCart(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String productId = request.getParameter("productId");
		String number = request.getParameter("number");

		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户未登录！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (productId == null || productId.equals("")) {
				rsInfo.setMessage("商品ID不能为空！");
				rsInfo.setCode("9997");
			return rsInfo;
		}
		Product product = productService.getProductByProductId(Integer.parseInt(productId));
		if(product == null){
			rsInfo.setMessage("商品不存在！");
			rsInfo.setCode("9996");
			return rsInfo;
		}
		if (number == null || "".equals(number)) {
			rsInfo.setMessage("商品数量不能为空！");
			rsInfo.setCode("9997");
			return rsInfo;
		}else if(Integer.parseInt(number)<=0){
			rsInfo.setMessage("商品数量不能小于0！");
			rsInfo.setCode("9996");
			return rsInfo;
		}
		ShoppingCartKey shoppingCartKey = new ShoppingCartKey();
		shoppingCartKey.setProductId(Integer.parseInt(productId));
		shoppingCartKey.setUserId(Integer.parseInt(userId));
		ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByShoppingCartId(shoppingCartKey);
		if(shoppingCart == null){
			rsInfo.setMessage("购物车不存在！");
			rsInfo.setCode("9998");
			return rsInfo;
		}else if(!shoppingCart.getProductId().equals(productId)){//判断购物车中商品id与要参数中的商品id是否一致
			rsInfo.setMessage("购物车中商品不一致！");
			rsInfo.setCode("9999");
			return rsInfo;
		}
		//判断库存是否足够
		Integer stock = product.getStock();
		if(stock == null || stock<Integer.parseInt(number)){
			rsInfo.setMessage("库存不足！");
			rsInfo.setCode("9994");
			return rsInfo;
		}
		shoppingCart.setUpdatedDate(new Date());
		shoppingCart.setNumber(Integer.parseInt(number));
		int count = 0;
		try {
			count = shoppingCartService.modifyShoppingCart(shoppingCart);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改购物车异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改购物车失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改购物车成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 修改购物车选中状态
	 * <p>
	 * 如果原来没有勾选，则设置勾选状态；如果商品已经勾选，则设置非勾选状态。
	 *
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/modifyChecked")
	public @ResponseBody Object deleteShoppingCart(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();
		String userId = request.getParameter("userId");
		String[] productIds = request.getParameterValues("productIds");
		String checked = request.getParameter("checked");
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户未登录！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (productIds == null || productIds.length==0) {
			rsInfo.setMessage("商品ID不能为空！");
			rsInfo.setCode("9999");
			return rsInfo;
		}
		if (checked == null || "".equals(checked)) {
			rsInfo.setMessage("选中状态不能为空！");
			rsInfo.setCode("9998");
			return rsInfo;
		}

		int count = 0;
		List<ShoppingCartKey> shoppingCartKeyList = new ArrayList<>();
		for (String productId : productIds) {
			ShoppingCartKey cartKey = new ShoppingCartKey();
			cartKey.setProductId(Integer.parseInt(productId));
			cartKey.setUserId(Integer.parseInt(userId));
			shoppingCartKeyList.add(cartKey);
		}
		try {
			//count = shoppingCartService.updateChecked(shoppingCartKeyList,Boolean.parseBoolean(checked));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改购物车异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("修改购物车成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("修改购物车失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * Description: 立即购买
	 * 和add方法的区别在于：
	 *  1. 如果购物车内已经存在购物车货品，前者的逻辑是数量添加，这里的逻辑是数量覆盖
	 *  2. 添加成功以后，前者的逻辑是返回当前购物车商品数量，这里的逻辑是返回对应购物车项的ID
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/shoppingCart/fastAddShoppingCart")
	public @ResponseBody Object getShoppingCartById(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String productId = request.getParameter("productId");
		String number = request.getParameter("number");
		if (userId == null || userId.equals("")) {
			rsInfo.setMessage("用户未登录！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (productId == null || productId.equals("")) {
			rsInfo.setMessage("商品ID不能为空！");
			rsInfo.setCode("9997");
			return rsInfo;
		}
		Product product = productService.getProductByProductId(Integer.parseInt(productId));
		if(product == null){
			rsInfo.setMessage("商品不存在！");
			rsInfo.setCode("9996");
			return rsInfo;
		}
		if (number == null || "".equals(number)) {
			rsInfo.setMessage("商品数量不能为空！");
			rsInfo.setCode("9997");
			return rsInfo;
		}else if(Integer.parseInt(number)<=0){
			rsInfo.setMessage("商品数量不能小于0！");
			rsInfo.setCode("9996");
			return rsInfo;
		}
		//判断库存是否足够
		Integer stock = product.getStock();
		if(stock == null || stock<Integer.parseInt(number)){
			rsInfo.setMessage("库存不足！");
			rsInfo.setCode("9994");
			return rsInfo;
		}

		int count = 0,i=0;
		try {
			ShoppingCartKey key = new ShoppingCartKey();
			key.setProductId(Integer.parseInt(productId));
			key.setUserId(Integer.parseInt(userId));
			//判断购物车中是否存在该商品
			ShoppingCart shoppingCartByShoppingCartId = shoppingCartService.getShoppingCartByShoppingCartId(key);
			if(shoppingCartByShoppingCartId != null){
				shoppingCartByShoppingCartId.setNumber(Integer.parseInt(number));
				shoppingCartByShoppingCartId.setUpdatedDate(new Date());
				shoppingCartService.modifyShoppingCart(shoppingCartByShoppingCartId);
			}else {
				ShoppingCart shoppingCart = new ShoppingCart();
				shoppingCart.setNumber(Integer.parseInt(number));
				shoppingCart.setUserId(Integer.parseInt(userId));
				shoppingCart.setProductId(Integer.parseInt(productId));
				shoppingCart.setCreatedDate(new Date());
				count = shoppingCartService.addShoppingCart(shoppingCart);
			}
			//同时库存减的数量为number
			product.setStock(stock - Integer.parseInt(number));
			product.setUpdatedDate(new Date());
			i = productService.modifyProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("立即加入购物车失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setData(count);
		rsInfo.setMessage("立即加入购物车成功！");
		return rsInfo;
	}

	/**
	 * @description 用户购物车列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/getShoppingCartList")
	public @ResponseBody Object getShoppingCartList(HttpServletRequest request, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();
		HashMap<String, Object> params = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		if (pageNum == null || pageNum.equals("")) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (pageSize == null || pageSize.equals("")) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if(userId != null && !"".equals(userId)){
			params.put("userId",Integer.parseInt(userId));
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount = 0;

		try {
			totalCount = shoppingCartService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取购物车总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (totalCount == 0) {
			rsInfo.setMessage("购物车总数量为0！");
			rsInfo.setCode("200");
			return rsInfo;
		}
		List<RsShoppingCart> shoppingCartList = null;
		try {
			shoppingCartList = shoppingCartService.getShoppingCartList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取购物车列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (shoppingCartList.size() == 0) {
			rsInfo.setMessage("购物车列表为空！");
			rsInfo.setTotal(0);
			rsInfo.setData(new ArrayList<ShoppingCart>());
			return rsInfo;
		}
		rsInfo.setTotal(totalCount);
		rsInfo.setData(shoppingCartList);
		return rsInfo;
	}

	/**
	 * 批量删除购物车
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/deleteShoppingCartBatch")
	public @ResponseBody Object deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String[] productIds = request.getParameterValues("productIds");
		String[] specifications = request
				.getParameterValues("specifications");
		

		if (userId == null || "".equals(userId)) {
			rsInfo.setMessage("用户未登录！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (productIds == null || productIds.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		List<ShoppingCartKey> shoppingCartKeyList = new ArrayList<>();
		for (int i =0; i< productIds.length; i++) {
			ShoppingCartKey cartKey = new ShoppingCartKey();
			cartKey.setProductId(Integer.parseInt(productIds[i]));
			cartKey.setSpecification(specifications[i]);
			cartKey.setUserId(Integer.parseInt(userId));
			
			shoppingCartKeyList.add(cartKey);
		}
		int count = 0;
		try {
			count = shoppingCartService.deleteBatch(shoppingCartKeyList);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("批量删除异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (count > 0) {
			rsInfo.setMessage("批量删除成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("批量删除失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}


	/**
	 * 购物车下单
	 *
	 * @param userId    用户ID
	 * @param productId    购物车商品ID：
	 *                  如果购物车商品ID是空，则下单当前用户所有购物车商品；
	 *                  如果购物车商品ID非空，则只下单当前购物车商品。
	 * @param addressId 收货地址ID：
	 *                  如果收货地址ID是空，则查询当前用户的默认地址。
	 * @param couponId  优惠券ID：
	 *                  如果优惠券ID是空，则自动选择合适的优惠券。
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/shoppingCart/checkout")
	public @ResponseBody Object checkout(HttpServletRequest request, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String[] productIds = request.getParameterValues("productIds");
		String addressId = request.getParameter("addressId");
		String couponId = request.getParameter("couponId");

		if (userId == null || "".equals(userId)) {
			rsInfo.setMessage("用户未登录！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (productIds == null || productIds.length < 1) {
			rsInfo.setMessage("商品ID不能为空！");
			rsInfo.setCode("9999");
			return rsInfo;
		}
		Integer addressId2 = Integer.parseInt(addressId);
		// 收货地址
		Address checkedAddress = null;
		if (addressId == null || addressId.equals(0)) {
			checkedAddress = addressService.getDefaultAddress(Integer.parseInt(userId));
			// 如果仍然没有地址，则是没有收获地址
			// 返回一个空的地址id=0，这样前端则会提醒添加地址
			if (checkedAddress == null) {
				checkedAddress = new Address();
				checkedAddress.setAddressId(0);
				addressId2 = 0;
			} else {
				addressId2 = checkedAddress.getAddressId();
			}
		} else {
			checkedAddress = addressService.getUserAddress(Integer.parseInt(userId), addressId2);
			// 如果null, 则报错
			if (checkedAddress == null) {
				rsInfo.setMessage("收货地址不存在！");
				rsInfo.setCode("9998");
				return rsInfo;
			}
		}

		//TODO 计算商品价格 优惠  邮费 等等

		List<ShoppingCart> checkedCartList = null;

		List<ShoppingCartKey> shoppingCartKeyList = new ArrayList<>();
		for (String productId : productIds) {
			ShoppingCartKey cartKey = new ShoppingCartKey();
			cartKey.setProductId(Integer.parseInt(productId));
			cartKey.setUserId(Integer.parseInt(userId));
			shoppingCartKeyList.add(cartKey);
		}
		int count = 0;
		try {
			count = shoppingCartService.deleteBatch(shoppingCartKeyList);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("商品异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (count > 0) {
			rsInfo.setMessage("批量删除成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("批量删除失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}


}
