package com.techwells.wumei.controller;

import com.techwells.wumei.domain.*;
import com.techwells.wumei.domain.rs.RsJoin;
import com.techwells.wumei.domain.rs.RsOrder;
import com.techwells.wumei.domain.rs.RsOrderInfo;
import com.techwells.wumei.service.*;
import com.techwells.wumei.util.FileUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import com.techwells.wumei.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
//@RequestMapping(value = "*.do")
@RestController
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	
	@Resource
	OrderProductService orderProductService;

	@Resource
	ProductService productService;
	
	@Resource
	StockService stockService;
	
	@Resource
	ShoppingCartService shoppingCartService;
	@Resource
	private MerchantService merchantService;

	@Resource
	private GrouponService grouponService;
	@Resource
	private JoinService joinService;
	@Resource
	private ExclusiveService exclusiveService;

	/**
	 * 添加订单
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/order/addOrder")
	public @ResponseBody Object addOrder(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String orderName = request.getParameter("orderName");

		if (orderName == null || orderName.equals("")) {
			rsInfo.setMessage("订单名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Order order = new Order();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "order") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}

		order.setCreatedDate(new Date());
		int count = 0;
		try {
			count = orderService.addOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("添加订单异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("添加订单成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("添加订单失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 修改订单信息
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/order/modifyOrder")
	public @ResponseBody Object modifyOrder(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile[] files) {
		ResultInfo rsInfo = new ResultInfo();

		String orderId = request.getParameter("orderId");
		String orderName = request.getParameter("orderName");

		if (orderId == null || orderId.equals("")) {
			rsInfo.setMessage("订单ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (orderName == null || orderName.equals("")) {
			rsInfo.setMessage("订单名称不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		Order order = new Order();

		// 处理图片
		if (files != null && files.length > 0) {
			String fileUrl = "";
			for (MultipartFile file : files) {
				try {
					fileUrl += FileUtil.uploadFile(file, "order") + ",";
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			if (!fileUrl.equals("")) {
				fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
			}
		}
		order.setOrderId(Integer.parseInt(orderId));
		order.setUpdatedDate(new Date());
		int count = 0;
		try {
			count = orderService.modifyOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("修改订单异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count < 1) {
			rsInfo.setMessage("修改订单失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		rsInfo.setMessage("修改订单成功！");
		rsInfo.setData(count);
		return rsInfo;
	}

	/**
	 * Description: 删除订单
	 * 
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/order/deleteOrder")
	public @ResponseBody Object deleteOrder(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String orderId = request.getParameter("orderId");
		if (orderId == null || orderId.equals("")) {
			rsInfo.setMessage("订单Id不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		int count = 0;
		try {
			count = orderService.delOrder(Integer.parseInt(orderId));
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("删除订单异常!");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (count > 0) {
			rsInfo.setMessage("删除订单成功！");
			rsInfo.setData(count);
		} else {
			rsInfo.setMessage("删除订单失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		return rsInfo;
	}

	/**
	 * 查看订单详情
	 * @param orderId 订单id
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/order/getOrderById/{orderId}")
	public @ResponseBody Object getOrderById(@PathVariable("orderId") Integer orderId) {
		ResultInfo rsInfo = new ResultInfo();
		
		RsOrder order;
		try {
			order = orderService.getOrderByOrderId(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("获取订单信息失败！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (order == null) {
			rsInfo.setMessage("订单信息不存在！");
			rsInfo.setData(new Order());
			rsInfo.setTotal(0);
			return rsInfo;
		}
		rsInfo.setData(order);
		rsInfo.setMessage("获取订单成功！");
		return rsInfo;
	}

	/**
	 * @description 获取订单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/order/getOrderList")
	public @ResponseBody Object getOrderList(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>(16);

		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String orderId = request.getParameter("orderId");
		//收货人姓名/手机号码
		String receiverKeyword = request.getParameter("receiverKeyword");
		String userId = request.getParameter("userId");
		String merchantId = request.getParameter("merchantId");
		String status = request.getParameter("status");
		String createTime = request.getParameter("createTime");
		String orderType = request.getParameter("orderType");
		String sourceType = request.getParameter("sourceType");

		if (pageNum == null || "".equals(pageNum)) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (pageSize == null || "".equals(pageSize)) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		if (userId != null && !"".equals(userId)) {
			params.put("userId", userId);
		}
		
		if (merchantId != null && !"".equals(merchantId)) {
			params.put("merchantId", merchantId);
		}
		
		if (status != null && !"".equals(status)) {
			params.put("status", status);
		}

		if (orderId != null && !"".equals(orderId)) {
			params.put("orderId", orderId);
		}

		if (receiverKeyword != null && !"".equals(receiverKeyword)) {
			params.put("receiverKeyword", receiverKeyword);
		}

		if (createTime != null && !"".equals(createTime)) {
			params.put("createTime", createTime);
		}

		if (orderType != null && !"".equals(orderType)) {
			params.put("orderType", orderType);
		}

		if (sourceType != null && !"".equals(sourceType)) {
			params.put("sourceType", sourceType);
		}

		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = orderService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取订单总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("订单总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsOrder> orderList;
		try {
			orderList = orderService.getOrderList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取订单列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (orderList.size() == 0) {
			rsInfo.setMessage("订单列表为空！");
			rsInfo.setData(new ArrayList<Order>());
			return rsInfo;
		}
		rsInfo.setData(orderList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取订单列表成功！");
		return rsInfo;
	}
	
	/**
	 * @description 获取用户订单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/order/getMyOrderList")
	public @ResponseBody Object getMyOrderList(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		HashMap<String, Object> params = new HashMap<>(16);

		String userId = request.getParameter("userId");
		String status = request.getParameter("status");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		if (pageNum == null || "".equals(pageNum)) {
			rsInfo.setMessage("页码不能为空！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		
		if (pageSize == null || "".equals(pageSize)) {
			rsInfo.setMessage("页大小不能为空！");
			rsInfo.setCode("10002");
			return rsInfo;
		}


		PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		
		
		if (status != null && !"".equals(status)) {
			params.put("status", status);
		}
		
		
		if (userId == null || "".equals(userId)) {
			
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
			
		}else {
			
			params.put("userId", userId);
			
		}
		
		
		pageTool.setParams(params);
		int totalCount;

		try {
			totalCount = orderService.countTotal(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取用户订单总数异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}

		if (totalCount == 0) {
			rsInfo.setMessage("用户订单总数量为0！");
			rsInfo.setCode("23211");
			return rsInfo;
		}

		List<RsOrder> orderList;
		try {
			orderList = orderService.getOrderList(pageTool);
		} catch (Exception e) {
			rsInfo.setMessage("获取用户订单列表异常！");
			rsInfo.setCode("10001");
			return rsInfo;
		}
		if (orderList.size() == 0) {
			rsInfo.setMessage("用户订单列表为空！");
			rsInfo.setData(new ArrayList<Order>());
			return rsInfo;
		}
		rsInfo.setData(orderList);
		rsInfo.setTotal(totalCount);
		rsInfo.setMessage("获取用户订单列表成功！");
		return rsInfo;
	}

	/**
	 * 批量删除订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/order/deleteOrderBatch")
	public @ResponseBody Object deleteBatch(HttpServletRequest request,
			HttpServletResponse response) {
		ResultInfo rsInfo = new ResultInfo();

		String[] idArr = request.getParameterValues("id");

		if (idArr == null || idArr.length < 1) {
			rsInfo.setMessage("ID不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}

		int count = 0;
		try {
			count = orderService.deleteBatch(idArr);
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
	 * Description: 发货
	 * 
	 * @param orderId
	 *            订单ID
	 * @param opId
	 *            订单商品ID
	 * @param batch
	 *            批次
	 * @param qrcode
	 *            二维码
	 * @param deliveryWay
	 *            配送方式
	 * @param expressCode
	 *            物流单号
	 * @param expressExpenses
	 *            物流费
	 * @param expressCompany
	 *            物流公司
	 * 
	 * @return count
	 * 
	 * 
	 */
	@RequestMapping(value = "/order/sendProduct")
	public @ResponseBody Object sendProduct(HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
		
		ResultInfo rsInfo = new ResultInfo();
		
		String orderId = request.getParameter("orderId");
		String expressCode = request.getParameter("expressCode");
		/* String expressExpenses = request.getParameter("expressExpenses"); */
		String expressCompany = request.getParameter("expressCompany");
		
		if (orderId == null || "".equals(orderId)) {
			rsInfo.setMessage("订单ID不能为空！");
			rsInfo.setCode("35136");
			return rsInfo;
		}

		if (expressCompany == null || "".equals(expressCompany)) {
			rsInfo.setMessage("物流公司不能为空！");
			rsInfo.setCode("35141");
			return rsInfo;
		}
		if (expressCode == null || "".equals(expressCode)) {
			rsInfo.setMessage("物流单号不能为空！");
			rsInfo.setCode("35142");
			return rsInfo;
		}
		/*
		 * if (expressExpenses == null || expressExpenses.equals("")) {
		 * rsInfo.setMessage("物流费不能为空！"); rsInfo.setCode("35143");
		 * return rsInfo; }
		 */

		Order order = new Order();
		int count;
		int oid = Integer.parseInt(orderId);
		try {

			order.setOrderId(oid);
			order.setStatus(3);
			order.setExpressCode(expressCode);
			order.setExpressCompany(expressCompany);
			count = orderService.modifyOrder(order);

			if (count < 0) {
				rsInfo.setCode("35319");
				rsInfo.setMessage("发货失败！");
				return rsInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setCode("35221");
			rsInfo.setMessage("发货失败！");
			return rsInfo;
		}

		rsInfo.setMessage("发货成功！");
		rsInfo.setData(count);
		return rsInfo;
	}


	/**
	 *  生成订单
	 *
	 * userId 用户id
	 * productIds 商品id
	 * productNums 商品数量
	 * addressId 地址信息
	 * userMessage 用户留言(可选)
	 * sourceType 订单类型 1 购物车 2 直接购买 3 开团 4 参团 5 会员专属
	 * sourceId 团购活动id
	 * distributionMode 自取还是发快递
	 * specifications 规格""或值，按照顺序传
	 * merchantId
	 *            商家ID
	 *  mpId
	 *            商品ID
	 *  productNum
	 *            商品数量
	 *  maId
	 *            活动商品ID
	 *  couponId
	 *            优惠券ID(可选)
	 *  addressId
	 *            收货地址ID
	 *  distributionMode
	 *            配送方式
	 *  arrivalTime
	 *            送达时间(可选)
	 *  userMessage
	 *            用户留言(可选)
	 * @param request 请求
	 * @return ResultInfo
	 */
	@RequestMapping(value = "/order/generateOrder")
	public @ResponseBody Object generateOrder(HttpServletRequest request) {
		ResultInfo rsInfo = new ResultInfo();

		String userId = request.getParameter("userId");
		String[] productIds = request.getParameterValues("productIds");
		String[] productNums = request.getParameterValues("productNums");
		String addressId = request.getParameter("addressId");
		String userMessage = request.getParameter("userMessage");
		// 1 购物车 2 直接购买 3 开团 4 参团 5 会员专属
		String sourceType = request.getParameter("sourceType");
		// 团购活动ID
		String sourceId = request.getParameter("sourceId");
		// 自取还是发快递
		String distributionMode = request.getParameter("distributionMode");
		//商品店铺id
		String merchantId = request.getParameter("merchantId");


		// 前端specifications参数：""或值，按照顺序传
		String[] specifications = request
				.getParameterValues("specifications");

		if (userId == null || "".equals(userId)) {
			rsInfo.setMessage("用户ID不能为空！");
			rsInfo.setCode("35117");
			return rsInfo;
		}
		if (addressId == null || "".equals(addressId)) {
			rsInfo.setMessage("地址ID不能为空！");
			rsInfo.setCode("35118");
			return rsInfo;
		}

		if (productNums == null || productNums.length == 0) {
			rsInfo.setMessage("商品数量不能为空！");
			rsInfo.setCode("35123");
			return rsInfo;
		}
		if (productIds == null || productIds.length == 0) {
			rsInfo.setMessage("商品ID不能为空！");
			rsInfo.setCode("35123");
			return rsInfo;
		}

		if (specifications == null || specifications.length == 0) {
			rsInfo.setMessage("规格不能为空！");
			rsInfo.setCode("35123");
			return rsInfo;
		}

		if (StringUtil.isEmpty(merchantId)) {
			rsInfo.setMessage("商品店铺id不能为空！");
			rsInfo.setCode("35123");
			return rsInfo;
		}

		Order order;
		OrderProduct orderProduct;
		// 生成商品订单时用到的容器
		List<ReceiveShopping> shoppingList = new ArrayList<>();

		for (int i = 0; i < productIds.length; i++) {
			double salePrice;
			double purchasePrice;
			try {

				Product product = productService.getProductByProductId(Integer
						.parseInt(productIds[i]));
				if (product == null) {
					rsInfo.setMessage("获取商品信息失败！");
					rsInfo.setCode("35314");
					return rsInfo;
				}
				// 判断库存

				Stock s = new Stock();
				s.setProductId(Integer.parseInt(productIds[i]));
				s.setSpecification(specifications[i]);

				Stock st = stockService.getStockByStock(s);

				if (st == null) {
					rsInfo.setMessage("商品" + product.getProductName() + "规格" + specifications[i] + "不存在！");
					rsInfo.setCode("35314");
					return rsInfo;
				}

				if (st.getStock() < Integer.parseInt(productNums[i])) {
					rsInfo.setMessage("商品" + product.getProductName() + "规格" + specifications[i] + "库存不足！");
					rsInfo.setCode("35315");
					return rsInfo;
				}

				salePrice = st.getSalePrice();
				purchasePrice = st.getPurchasePrice();

				//判断订单来源
				if ("3".equals(sourceType) && sourceId != null && !"".equals(sourceId)) {

					Groupon groupon = grouponService.getGrouponByGrouponId(Integer.parseInt(sourceId));
					purchasePrice = groupon.getCurrentPrice();
				} else if ("4".equals(sourceType) && sourceId != null && !"".equals(sourceId)) {

					RsJoin join = joinService.getJoinByJoinId(Integer.parseInt(sourceId));
					purchasePrice = join.getCurrentPrice();
				} else if ("5".equals(sourceType) && sourceId != null && !"".equals(sourceId)) {

					Exclusive exclusive = exclusiveService.getExclusiveByExclusiveId(Integer.parseInt(sourceId));
					purchasePrice = exclusive.getCurrentPrice();
				}

			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setMessage("获取活动商品及非活动商品信息失败！");
				rsInfo.setCode("35214");
				return rsInfo;
			}

			ReceiveShopping shopping = new ReceiveShopping(
					Integer.parseInt(productIds[i]),
					Integer.parseInt(productNums[i]),
					salePrice,
					purchasePrice,
					specifications[i]);

			shoppingList.add(shopping);
		}

		int orderResult;
		double turnOver = 0;
		for (ReceiveShopping receiveShopping : shoppingList) {

			double salePrice = receiveShopping.getSalePrice();
			int productNum = receiveShopping.getProductNum();


			turnOver += salePrice * productNum;
		}
		order = new Order();
		order.setUserId(Integer.parseInt(userId));
		order.setAddressId(Integer.parseInt(addressId));
		order.setSourceType(Integer.parseInt(sourceType));
		order.setOrderAmount(turnOver);
		order.setUserMessage(userMessage);


		if (distributionMode != null && !"".equals(distributionMode)) {
			order.setDistributionMode(Integer.parseInt(distributionMode));
		}
		//团购
		if ("3".equals(sourceType) || "4".equals(sourceType)) {
			if (sourceId != null && !"".equals(sourceId)) {
				order.setSourceId(Integer.parseInt(sourceId));
			}
		}

		//转化为String对象
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		order.setOrderSn(uuid);
		order.setStatus(1);
		order.setMerchantId(Integer.parseInt(merchantId));

		try {
			// 生成订单
			orderResult = orderService.addOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
			rsInfo.setMessage("生成订单失败！");
			rsInfo.setCode("35215");
			return rsInfo;
		}

		if (orderResult <= 0) {

			rsInfo.setMessage("生成订单失败！");
			rsInfo.setCode("35215");
			return rsInfo;

		}
		//开团
		if ("3".equals(sourceType) && sourceId != null && !"".equals(sourceId)) {
			Join join = new Join();
			join.setCreatorId(Integer.parseInt(userId));
			join.setGrouponId(Integer.parseInt(sourceId));
			join.setCreatedDate(new Date());
			join.setOrderId(order.getOrderId());
			String[] userIds = new String[1];
			userIds[0] = userId;
			join.setUserIds(userIds);
			join.setStatus(1);
			int joinCount = joinService.addJoin(join);
			if (joinCount <= 0) {
				rsInfo.setMessage("开团失败！");
				rsInfo.setCode("35215");
				return rsInfo;
			}

		} else if ("4".equals(sourceType) && sourceId != null && !"".equals(sourceId)) {

			Join join = new Join();
			join.setJoinId(Integer.parseInt(sourceId));
			RsJoin j = joinService.getJoinByJoinId(Integer.parseInt(sourceId));
			String[] userIds = new String[j.getUserIds().length + 1];

			for (int i = 0; i < j.getUserIds().length + 1; i++) {

				if (i == j.getUserIds().length) {
					userIds[i] = userId;

				} else {
					String uid = j.getUserIds()[i];
					userIds[i] = uid;
				}
			}

			join.setUserIds(userIds);

			int joinCount = joinService.modifyJoin(join);

			if (joinCount <= 0) {
				rsInfo.setMessage("参团失败！");
				rsInfo.setCode("35215");
				return rsInfo;
			}
		}
		// 减库存
		for (int i = 0; i < productIds.length; i++) {
			int num = Integer.parseInt(productNums[i]);
			try {

				Product product = productService.getProductByProductId(Integer
						.parseInt(productIds[i]));
				if (product == null) {
					rsInfo.setMessage("获取商品信息失败！");
					rsInfo.setCode("35314");
					return rsInfo;
				}


				Stock s = new Stock();
				s.setProductId(Integer.parseInt(productIds[i]));
				s.setSpecification(specifications[i]);

				Stock st = stockService.getStockByStock(s);

				if (null == st) {
					rsInfo.setMessage("商品" + product.getProductName() + "规格" + specifications[i] + "没有库存！");
					rsInfo.setCode("35314");
					return rsInfo;
				}
				if (st.getStock() < Integer.parseInt(productNums[i])) {
					rsInfo.setMessage("商品" + product.getProductName() + "规格" + specifications[i] + "库存不足！");
					rsInfo.setCode("35315");
					return rsInfo;
				}

				st.setStock(st.getStock() - num);
				stockService.modifyStock(st);

			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setMessage("获取活动商品及非活动商品信息失败！");
				rsInfo.setCode("35214");
				return rsInfo;
			}

		}

		if ("1".equals(sourceType)) {
			// 生成订单后删除对应的购物车商品
			for (int i = 0; i < productIds.length; i++) {
				ShoppingCartKey sck = new ShoppingCartKey();
				sck.setUserId(Integer.parseInt(userId));
				sck.setProductId(Integer.parseInt(productIds[i]));
				if (specifications[i] != null) {
					sck.setSpecification(specifications[i]);
				}
				try {
					shoppingCartService.delShoppingCart(sck);
				} catch (Exception e) {
					rsInfo.setMessage("删除购物车失败！");
					rsInfo.setCode("35216");
					return rsInfo;
				}
			}
		}


		for (ReceiveShopping shopping : shoppingList) {
			try {

				orderProduct = new OrderProduct();
				orderProduct.setOrderId(order.getOrderId());
				orderProduct.setProductId(shopping.getProductId());
				orderProduct.setNumber(shopping.getProductNum());
				orderProduct.setSalePrice(shopping.getSalePrice());
				orderProduct.setActivityPrice(shopping.getActivityPrice());
				orderProduct.setSpecification(shopping.getSpecification());

				if ("3".equals(sourceType) && sourceId != null && !"".equals(sourceId)) {
					orderProduct.setActivityId(1);
					orderProduct.setActivityId(Integer.parseInt(sourceId));

				} else if ("4".equals(sourceType) && sourceId != null && !"".equals(sourceId)) {
					orderProduct.setActivityId(1);
					RsJoin join = joinService.getJoinByJoinId(Integer.parseInt(sourceId));
					orderProduct.setActivityId(join.getGrouponId());
				} else if ("5".equals(sourceType) && sourceId != null && !"".equals(sourceId)) {
					orderProduct.setActivityId(2);
					orderProduct.setActivityId(Integer.parseInt(sourceId));

				}

				orderProductService.addOrderProduct(orderProduct);

			} catch (Exception e) {
				e.printStackTrace();
				rsInfo.setMessage("生成订单商品失败！");
				rsInfo.setCode("35217");
				return rsInfo;
			}

		}

		rsInfo.setMessage("生成订单成功！");
		rsInfo.setData(order);
		return rsInfo;
	}

	/**
	 * 备注订单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/order/update/note")
	public ResultInfo updateDescription(HttpServletRequest request){
		ResultInfo resultInfo = new ResultInfo();
		String orderId = request.getParameter("orderId");
		String description = request.getParameter("description");

		if (orderId == null || "".equals(orderId)) {
			resultInfo.setMessage("订单名称不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}

		if (description == null || "".equals(description)) {
			resultInfo.setMessage("订单名称不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}


		int count = orderService.updateOrderDescription(orderId,description);

		if (count > 0) {
			resultInfo.setMessage("备注订单成功！");
			resultInfo.setData(count);
		} else {
			resultInfo.setMessage("备注订单失败！");
			resultInfo.setCode("10001");
			return resultInfo;
		}

		return resultInfo;
	}

	/**
	 * 批量操作订单，发货，关闭，删除
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/order/batchDeleteOrder")
	public ResultInfo batchOperationOrder(HttpServletRequest request){
		ResultInfo resultInfo = new ResultInfo();

		String ids = request.getParameter("ids");
		String orderType = request.getParameter("type");
		String[] idArrays = ids.split(",");
		if (null == ids || "".equals(ids)) {
			resultInfo.setMessage("订单id不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}

		if (null == orderType || "".equals(orderType)) {
			resultInfo.setMessage("操作类型不能为空！");
			resultInfo.setCode("10000");
			return resultInfo;
		}

		int count;
		try {
			count = orderService.batchOperationOrder(idArrays,orderType);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setMessage("批量操作异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}

		if (count > 0) {
			resultInfo.setMessage("批量操作成功！");
			resultInfo.setData(count);
		} else {
			resultInfo.setMessage("批量操作失败！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		return resultInfo;
	}

	/**
	 * 商家订单管理
	 *
	 * @param merchantId  商家id
	 * @param orderStatus 订单状态
	 * @return ResultInfo
	 */
	@ResponseBody
	@RequestMapping("/order/getOrderListByMerchantId")
	public ResultInfo getOrderListByMerchantId(@RequestParam("merchantId") Integer merchantId,
											   @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
											   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
											   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
		ResultInfo resultInfo = new ResultInfo();
		List<RsOrder> rsOrderList;

		PagingTool pagingTool = new PagingTool(pageNum, pageSize);
		Map<String, Object> map = new HashMap<>(16);
		map.put("merchantId", merchantId);

		if (null == orderStatus) {
			map.put("orderStatus", orderStatus);
		}
		pagingTool.setParams(map);

		int totalCount;
		try {
			totalCount = orderService.getOrderCount(pagingTool);
		} catch (Exception e) {
			resultInfo.setMessage("获取订单总数异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		try {

			rsOrderList = orderService.getOrderList(pagingTool);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setMessage("查询商户订单异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}

		resultInfo.setData(totalCount);
		resultInfo.setTotal(rsOrderList.size());
		return resultInfo;
	}

	/**
	 * 商家订单详情
	 * @param orderId 订单id
	 * @return ResultInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/order/getMerchantOrderInfo")
	public ResultInfo getMerchantOrderInfo(@RequestParam("orderId")Integer orderId){
		ResultInfo resultInfo = new ResultInfo();
		RsOrderInfo rsOrderInfo;
		try {

			rsOrderInfo = orderService.selectMerchantOrderInfo(orderId);
			if (rsOrderInfo == null) {
				resultInfo.setMessage("订单信息不存在！");
				resultInfo.setData(new Order());
				resultInfo.setTotal(0);
				return resultInfo;
			}
			//查询店铺信息
			Merchant merchant = merchantService.getMerchantByMerchantId(rsOrderInfo.getMerchantId());
			rsOrderInfo.setMerchantId(merchant.getUserId());
			rsOrderInfo.setMerchantName(merchant.getMerchantName());



		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setMessage("获取订单信息失败！");
			resultInfo.setCode("10001");
			return resultInfo;
		}

		resultInfo.setData(rsOrderInfo);
		return resultInfo;
	}

}
