package com.techwells.wumei.controller;

import com.alibaba.fastjson.JSON;
import com.techwells.wumei.domain.*;
import com.techwells.wumei.domain.rs.*;
import com.techwells.wumei.service.*;
import com.techwells.wumei.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.techwells.wumei.util.ConstantUtil.SUCCESSCODE;
import static java.util.stream.Collectors.toList;


@RestController
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private SpecificationsService specificationsService;

    @Autowired
    private GrouponService grouponService;

    @Resource
    private StockService stockService;

    @Resource
    private JoinService joinService;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private EvaluationService evaluationService;

    @Resource
    private MerchantService merchantService;
    @Resource
    private OrderService orderService;

    /**
     * 添加商品
     *
     * @param request
     * @return
     */

    @PostMapping(value = "/product/addProduct")
    public @ResponseBody
    Object addProduct(HttpServletRequest request,
                      @RequestParam(value = "file", required = false) MultipartFile[] files,
                      @RequestParam(value = "iconFile", required = false) MultipartFile[] iconFiles) {
        ResultInfo rsInfo = new ResultInfo();

/*		String productName = request.getParameter("productName");
		String jumpUrl = request.getParameter("jumpUrl");
		String productType = request.getParameter("productType");
		String description = request.getParameter("description");
		String sortIndex = request.getParameter("sortIndex");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String brand = request.getParameter("brand");
		String merchantId = request.getParameter("merchantId");*/

        //商品
        Product product = JSON.parseObject(request.getParameter("product"), Product.class);
        //商品库存
        List<Stock> stock = JSON.parseArray(request.getParameter("stocks"), Stock.class);
        //商品规格
        List<SpecificationsProduct> specificationsList = JSON.parseArray(request.getParameter("specification"), SpecificationsProduct.class);

        String productSpecification = stock.get(0).getSpecification();

        String[] productSpecificationArray = productSpecification.substring
                ((productSpecification.indexOf("[") + 1), productSpecification.indexOf("]")).split(",");
        StringBuilder productSB = new StringBuilder();


        for (String s : productSpecificationArray) {
            s = s.substring(1, s.lastIndexOf("\""));
            productSB.append(s).append("*");
        }
        productSpecification = productSB.toString();

        //商品规格 json类型
        product.setSpecification(request.getParameter("specification"));

        //库存规格 XXX*XXX
        stock.get(0).setSpecification(productSpecification.substring(0, productSpecification.lastIndexOf("*")));

        int productStock = 0;
        for (Stock stock1 : stock) {
            productStock = productStock + stock1.getStock();
        }
        product.setStock(productStock);

/*		if (productType == null || productType.equals("")) {
			rsInfo.setMessage("product类型不能为空！");
			rsInfo.setCode("10000");
			return rsInfo;
		}
		if (sortIndex == null || "".equals(sortIndex)) {
			sortIndex ="9999";
		}

		Product product = new Product();
		product.setProductName(productName);

		product.setDescription(description);*/

        String imageIcon = null;
        String stockIcon = null;

        // 处理商品图片
        if (files != null && files.length > 0) {
            String fileUrl = "";
            for (MultipartFile file : files) {
                try {
                    fileUrl += FileUtil.uploadFile(file, "product") + ",";
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
            if (!"".equals(fileUrl)) {
                fileUrl = fileUrl.substring(0, fileUrl.length() - 1);

                imageIcon = fileUrl;
            }
        }

        // 处理商品规格图片
        if (iconFiles != null && iconFiles.length > 0) {
            String fileUrl = "";
            for (MultipartFile file : iconFiles) {
                try {
                    fileUrl += FileUtil.uploadFile(file, "product") + ",";
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
            if (!"".equals(fileUrl)) {
                fileUrl = fileUrl.substring(0, fileUrl.length() - 1);

                stockIcon = fileUrl;
            }
        }

        product.setCreatedDate(new Date());
        int count;
        try {
            if (StringUtils.isNotEmpty(imageIcon)) {

                product.setProductIcon(imageIcon.substring(0, imageIcon.indexOf(",")));
            }
            //插入商品表
            count = productService.addProduct(product);

            //插入商品图片
            if (!StringUtils.isEmpty(product.getProductIcon()) && StringUtils.isNotEmpty(imageIcon)) {

                String[] imageUrlArray = imageIcon.split(",");
                productImageService.batchAddImage(imageUrlArray, product.getProductId());
            }

            //插入商品规格图片
            if (StringUtils.isNotEmpty(stockIcon)) {

                String[] stockIconArray = stockIcon.split(",");

                productImageService.batchAddImage(stockIconArray, product.getProductId());
            }

            //插入库存表
            stock.get(0).setProductId(product.getProductId());
            stock.get(0).setUrl(stockIcon);
            stock.get(0).setStockId(null);

            stockService.addStock(stock.get(0));


            //插入商品详情表
            specificationsList.forEach(data -> data.setProductId(product.getProductId()));
            specificationsService.batchInsertSpecification(specificationsList);

        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("添加商品异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("添加商品成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("添加商品失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        rsInfo.setMessage("添加商品成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * 修改商品信息
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/product/modifyProduct/{productId}")
    public @ResponseBody
    Object modifyProduct(HttpServletRequest request,
                         @RequestParam(value = "file", required = false) MultipartFile[] files,
                         @PathVariable("productId") Integer productId) {
        ResultInfo rsInfo = new ResultInfo();

//		String productName = request.getParameter("productName");
//		String jumpUrl = request.getParameter("jumpUrl");
//		String productType = request.getParameter("productType");
//		String description = request.getParameter("description");
//		String sortIndex = request.getParameter("sortIndex");
//		String startDate = request.getParameter("startDate");
//		String endDate = request.getParameter("endDate");

        if (productId == null) {
            rsInfo.setMessage("productID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
/*		if (sortIndex == null || sortIndex.equals("")) {
			sortIndex ="9999";
		}

		Product product = new Product();
		product.setProductId(productId);
		product.setProductName(productName);
		product.setDescription(description);*/
        //商品
        Product product = JSON.parseObject(request.getParameter("product"), Product.class);
        //商品库存
        List<Stock> stock = JSON.parseArray(request.getParameter("stocks"), Stock.class);

        //更新前的商品规格
        List<SpecificationsProduct> oldSpecificationsList = specificationsService.getSpecificationsList(productId);

        //更新后的商品规格
        List<SpecificationsProduct> specificationsList = JSON.parseArray(request.getParameter("specification"), SpecificationsProduct.class);

        //交集
        List<SpecificationsProduct> updateList = oldSpecificationsList.stream().filter(specificationsList::contains).collect(toList());
        //旧规格-交集 = 要删除的
        List<SpecificationsProduct> deleteList = oldSpecificationsList.stream().filter(item -> !updateList.contains(item)).collect(toList());
        //新规格-交集 = 要插入的
        List<SpecificationsProduct> insertList = specificationsList.stream().filter(data -> !updateList.contains(data)).collect(toList());


        insertList.stream().forEach(data -> data.setProductId(productId));
        specificationsService.batchInsertSpecification(insertList);
        deleteList.stream().forEach(data -> data.setDeleted(true));
        specificationsService.deleteBatch(deleteList);

        specificationsService.batchUpdateSpecification(updateList);


        product.setSpecification(stock.get(0).getSpecification());


        int productStock = 0;
        for (Stock stock1 : stock) {
            productStock = productStock + stock1.getStock();
        }
        product.setStock(productStock);

        // 处理图片
        if (files != null && files.length > 0) {
            String fileUrl = "";
            for (MultipartFile file : files) {
                try {
                    fileUrl += FileUtil.uploadFile(file, "product") + ",";
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            }
            if (!fileUrl.equals("")) {
                fileUrl = fileUrl.substring(0, fileUrl.length() - 1);
            }
        }
        product.setUpdatedDate(new Date());
        int count = 0;
        try {
            count = productService.modifyProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("修改商品异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count < 1) {
            rsInfo.setMessage("修改商品失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setMessage("修改商品成功！");
        rsInfo.setData(count);
        return rsInfo;
    }

    /**
     * Description: 删除商品
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "/product/deleteProduct")
    public @ResponseBody
    Object deleteProduct(HttpServletRequest request,
                         HttpSession session, HttpServletResponse response) {
        ResultInfo rsInfo = new ResultInfo();

        String productId = request.getParameter("productId");
        String deleteStatus = request.getParameter("deleteStatus");

        if (productId == null || productId.equals("")) {
            rsInfo.setMessage("商品Id不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        int count = 0;
        try {
            count = productService.delProduct(Integer.parseInt(productId), deleteStatus);
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("删除商品异常!");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (count > 0) {
            rsInfo.setMessage("删除商品成功！");
            rsInfo.setData(count);
        } else {
            rsInfo.setMessage("删除商品失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        return rsInfo;
    }


    /**
     * app查看商品详情
     *
     * @param productId
     * @return
     */
    @GetMapping(value = "/product/getProductById/{productId}")
    public @ResponseBody
    Object getProductById(@PathVariable("productId") Integer productId) {
        ResultInfo rsInfo = new ResultInfo();
        VoProduct voProduct = new VoProduct();
        Product product;
        try {
            //查询商品信息
            product = productService.getProductByProductId(productId);

            if (product == null) {
                rsInfo.setMessage("商品信息不存在！");
                rsInfo.setData(new Product());
                rsInfo.setTotal(0);
                return rsInfo;
            }

            //Object specificationList = specificationsService.getSpecificationVoList(productId);
            List<Groupon> grouponList = grouponService.getGrouponListByProductId(productId);
            //Brand brand = brandService.getBrandByBrandId(product.getBrandId());

            HashMap<String, Object> params = new HashMap<String, Object>();
            PagingTool pageTool = new PagingTool(1, 10);
            params.put("productId", productId);
            pageTool.setParams(params);
            List<ProductImage> productImageList = productImageService.getProductImageList(pageTool);


            //进货单
            PagingTool stockPt = new PagingTool(1, 999);
            params.put("productId", productId);
            pageTool.setParams(params);

            //List<Stock> stockList = stockService.getStockList(stockPt);
            //库存列表
            List<Stock> stockList = stockService.getStockListByProductId(pageTool);

            PagingTool joinPt = new PagingTool(1, 999);
            params.put("productId", productId);
            pageTool.setParams(params);
            List<RsJoin> joinList = joinService.getJoinList(joinPt);

            //评论
            PagingTool pageTool2 = new PagingTool(1, 999);
            params.put("productId", productId);
            pageTool.setParams(params);
            List<RsEvaluation> evaluationList = evaluationService.getEvaluationList(pageTool2);

            //商品规格
            List<SpecificationsProduct> specificationsList = specificationsService.getSpecificationsList(productId);

            voProduct.setProductId(product.getProductId());
            voProduct.setBrandId(product.getBrandId());
            voProduct.setSalePrice(product.getSalePrice());
            voProduct.setPurchasePrice(product.getPurchasePrice());
            voProduct.setProductIcon(product.getProductIcon());
            voProduct.setDescription(product.getDescription());
            voProduct.setIsScoreProduct(product.getIsScoreProduct());
            voProduct.setScoreProportion(product.getScoreProportion());
            voProduct.setSpecification(product.getSpecification());
            voProduct.setProductName(product.getProductName());
            voProduct.setProductionPlace(product.getProductionPlace());
            voProduct.setProductProfile(product.getProductProfile());
            voProduct.setProductType(product.getProductType());
            voProduct.setUnit(product.getUnit());
            voProduct.setStock(product.getStock());
            //voProduct.setBrand(brand);
            voProduct.setMaterial(product.getMaterial());
            voProduct.setEvaluationList(evaluationList);
            voProduct.setGrouponList(grouponList);
            voProduct.setImageList(productImageList);
            voProduct.setStockList(stockList);
            voProduct.setJoinList(joinList);
            voProduct.setSpecificationsList(specificationsList);

        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取商品信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setData(voProduct);
        rsInfo.setMessage("获取商品成功！");
        return rsInfo;
    }


    /**
     * @param request
     * @param response
     * @return
     * @description 获取商品列表
     */
    @GetMapping(value = "/product/getProductList")
    public @ResponseBody
    Object getProductList(HttpServletRequest request,
                          HttpServletResponse response) {
        ResultInfo rsInfo = new ResultInfo();

        HashMap<String, Object> params = new HashMap<>(16);

        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        //商品名称
        String productName = request.getParameter("productName");
        //商品id
        String productId = request.getParameter("productId");
        //上架状态
        String publishStatus = request.getParameter("publishStatus");
        //审核状态
        String verifyStatus = request.getParameter("verifyStatus");
        //商品分类
        String productType = request.getParameter("productType");
        //店铺id
        String merchantId = request.getParameter("merchantId");
		/*String priceOrder = request.getParameter("priceOrder");
		String startPrice = request.getParameter("startPrice");
		String endPrice = request.getParameter("endPrice");*/
        //商品品牌
        String brandId = request.getParameter("brandId");

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

        if (productName != null && !"".equals(productName)) {
            params.put("productName", productName);
        }

        if (productType != null && !"".equals(productType)) {
            params.put("productType", productType);
        }
        if (productId != null && !"".equals(productId)) {
            params.put("productId", productId);
        }

        if (brandId != null && !"".equals(brandId)) {
            params.put("brandId", brandId);
        }
        if (publishStatus != null && !"".equals(publishStatus)) {
            params.put("publishStatus", publishStatus);
        }
        if (verifyStatus != null && !"".equals(verifyStatus)) {
            params.put("verifyStatus", verifyStatus);
        }
        if (merchantId != null && !"".equals(merchantId)) {
            params.put("merchantId", merchantId);
        }

        PagingTool pageTool = new PagingTool(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize));
        pageTool.setParams(params);
        int totalCount;

        try {
            totalCount = productService.countTotal(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取商品总数异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        if (totalCount == 0) {
            rsInfo.setMessage("商品总数量为0！");
            rsInfo.setCode("23211");
            return rsInfo;
        }

        List<Product> productList;
        try {
            productList = productService.getProductList(pageTool);
        } catch (Exception e) {
            rsInfo.setMessage("获取商品列表异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (productList.size() == 0) {
            rsInfo.setMessage("商品列表为空！");
            rsInfo.setData(new ArrayList<Product>());
            return rsInfo;
        }
        rsInfo.setData(productList);
        rsInfo.setTotal(totalCount);
        rsInfo.setMessage("获取商品列表成功！");
        return rsInfo;
    }

    /**
     * 批量删除商品
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/product/deleteProductBatch")
    public @ResponseBody
    Object deleteBatch(HttpServletRequest request,
                       HttpServletResponse response) {
        ResultInfo rsInfo = new ResultInfo();

        String idArr = request.getParameter("productIds");

        if (StringUtil.isEmpty(idArr)) {
            rsInfo.setMessage("ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;
        }
        String[] productIds = idArr.split(",");

        int count = 0;
        try {
            count = productService.deleteBatch(productIds);
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
     * @param request
     * @param response
     * @return
     * @description 获取确认订单商品列表
     */
    @GetMapping(value = "/product/getConfirmProductList")
    public @ResponseBody
    Object getConfirmProductList(HttpServletRequest request,
                                 HttpServletResponse response) {
        ResultInfo rsInfo = new ResultInfo();

        String[] productIds = (String[]) request.getParameterValues("productIds");
        String[] specifications = (String[]) request.getParameterValues("specifications");
        String[] numbers = (String[]) request.getParameterValues("numbers");


        if (productIds == null) {

            rsInfo.setMessage("产品ID不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;

        }
        if (specifications == null) {

            rsInfo.setMessage("规格不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;

        }
        if (numbers == null) {

            rsInfo.setMessage("数量不能为空！");
            rsInfo.setCode("10000");
            return rsInfo;

        }

        List<RsProduct> productList = new ArrayList<RsProduct>();
        try {

            for (int i = 0; i < productIds.length; i++) {

                Product product = productService.getProductByProductId(Integer.parseInt(productIds[i]));
                if (product == null) {
                    continue;
                }

                RsProduct rsProduct = new RsProduct();
                rsProduct.setProductIcon(product.getProductIcon());
                rsProduct.setProductId(product.getProductId());
                rsProduct.setProductName(product.getProductName());
                rsProduct.setSalePrice(product.getSalePrice());
                rsProduct.setNumber(Integer.parseInt(numbers[i]));
                rsProduct.setSpecification(specifications[i]);
                productList.add(rsProduct);

            }
        } catch (Exception e) {
            rsInfo.setMessage("获取商品列表异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        if (productList.size() == 0) {
            rsInfo.setMessage("商品列表为空！");
            rsInfo.setData(new ArrayList<Product>());
            return rsInfo;
        }
        rsInfo.setData(productList);
        rsInfo.setMessage("获取商品列表成功！");
        return rsInfo;
    }

    /**
     * 更新上下架状态
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/product/updateIsOnSale")
    public ResultInfo updateProductStatus(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();

        String productId = request.getParameter("ids");
        String status = request.getParameter("isOnSale");

        if (StringUtil.isEmpty(productId)) {

            resultInfo.setMessage("productID不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(status)) {

            resultInfo.setMessage("商品状态不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }


        Product product = new Product();
        product.setProductId(Integer.parseInt(productId));

        int activated = "true".equals(status) ? 2 : 4;
        product.setActivated(activated);


        int count;
        try {
            count = productService.modifyProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改商品上下架状态异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count < 1) {
            resultInfo.setMessage("修改商品上下架状态失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setMessage("修改商品上下架状态成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 更新商品是否为积分商品状态
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/product/updateIsScoreProduct")
    public ResultInfo updateProductScopeStatus(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();

        String productId = request.getParameter("ids");
        String status = request.getParameter("isScoreProduct");

        if (StringUtil.isEmpty(productId)) {

            resultInfo.setMessage("productID不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        if (StringUtil.isEmpty(status)) {

            resultInfo.setMessage("商品状态不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        Product product = new Product();
        product.setProductId(Integer.parseInt(productId));

        product.setIsScoreProduct(Boolean.parseBoolean(status));

        int count;
        try {

            count = productService.modifyProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("修改商品积分商品状态异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count < 1) {
            resultInfo.setMessage("修改商品积分商品状态失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setMessage("修改商品积分商品状态成功！");
        resultInfo.setData(count);
        return resultInfo;
    }


    /**
     * 批量更新上下架状态
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/product/batchUpdateIsOnSale")
    public ResultInfo batchUpdateIsOnSale(HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();

        String productIds = request.getParameter("ids");
        String status = request.getParameter("isOnSale");

        if (StringUtil.isEmpty(productIds)) {

            resultInfo.setMessage("productId不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }

        if (StringUtil.isEmpty(status)) {

            resultInfo.setMessage("商品状态不能为空！");
            resultInfo.setCode("10000");
            return resultInfo;
        }
        String[] productIdArray = productIds.split(",");
        int count;
        try {

            count = productService.batchUpdateIsOnSale(productIdArray, status);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setMessage("批量更新上下架异常！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        if (count < 1) {
            resultInfo.setMessage("批量更新上下架失败！");
            resultInfo.setCode("10001");
            return resultInfo;
        }

        resultInfo.setMessage("批量更新上下架成功！");
        resultInfo.setData(count);
        return resultInfo;
    }

    /**
     * 好货推荐
     *
     * @param pageNum     页数
     * @param pageSize    页大小
     * @param productType 商品类型  0 灯光 1 音响 2 视频 3 舞台桁架 4 耗材周边
     * @param productName 商品名称
     * @param merchantId  商铺id
     * @return ResultInfo
     */
    @RequestMapping(value = "/product/getPopularProductList")
    public ResultInfo getPopularProductList(@RequestParam(value = "pageNum") Integer pageNum,
                                            @RequestParam(value = "pageSize") Integer pageSize,
                                            @RequestParam(value = "productType", required = false) String productType,
                                            @RequestParam(value = "productName", required = false) String productName,
                                            @RequestParam(value = "merchantId", required = false) String merchantId) {
		ResultInfo resultInfo = new ResultInfo();

        if (!SUCCESSCODE.equals(ParamCheckUtil.pagingParamsCheck(pageNum, pageSize, resultInfo).getCode())) {
            return resultInfo;
        }

		Map<String, Object> params = new HashMap<>(16);
		PagingTool pageTool = new PagingTool(pageNum, pageSize);
		if (!StringUtil.isEmpty(productType)) {

			params.put("productType", productType);
		}
		if (!StringUtil.isEmpty(productName)) {

			params.put("productName", productName);
		}
		if (!StringUtil.isEmpty(merchantId)) {

			params.put("merchantId", merchantId);
		}
		pageTool.setParams(params);

		int totalCount;
		try {
			totalCount = productService.popularProductCountTotal(pageTool);
		} catch (Exception e) {
			resultInfo.setMessage("获取好货推荐总数异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}
		if (0 == totalCount) {
			resultInfo.setMessage("暂无好货！");
			return resultInfo;
		}

		List<PopularProductVO> popularProductList;
		try {

			popularProductList = productService.getPopularList(pageTool);
		} catch (Exception e) {
			e.printStackTrace();

			resultInfo.setMessage("查询好货推荐商品异常！");
			resultInfo.setCode("10001");
			return resultInfo;
		}

		resultInfo.setData(popularProductList);
		resultInfo.setTotal(totalCount);
		return resultInfo;
	}

    /**
     * 获取好货推荐商品详细信息
     *
     * @param productId 商品id
     * @return ResultInfo
     */
    @ResponseBody
    @RequestMapping(value = "/product/getProductInfo", method = RequestMethod.GET)
    public ResultInfo getProductInfo(@RequestParam("productId") Integer productId) {
        ResultInfo rsInfo = new ResultInfo();
        ProductVo productVo = new ProductVo();
        Product product;
        try {

            //查询商品信息
            product = productService.getProductByProductId(productId);

            if (product == null) {
                rsInfo.setMessage("商品信息不存在！");
                rsInfo.setData(new Product());
                rsInfo.setTotal(0);
                return rsInfo;
            }
            //查询店铺信息
            Merchant merchantInfo = merchantService.getMerchantByMerchantId(product.getMerchantId());

            //查询商品图
            HashMap<String, Object> params = new HashMap<>(16);
            PagingTool pageTool = new PagingTool(1, 10);
            params.put("productId", productId);
            pageTool.setParams(params);
            List<ProductImage> productImageList = productImageService.getProductImageList(pageTool);

            //查询商品评论
            pageTool = new PagingTool(1, 999);
            pageTool.setParams(params);
            List<RsEvaluation> evaluationList = evaluationService.getEvaluationList(pageTool);
            //查询商品规格
            List<SpecificationsProduct> specificationsList = specificationsService.getSpecificationsList(productId);
            //查询商品订单

            int orderCount = orderService.countSale(product.getProductId());

            productVo.setProductId(product.getProductId());
            productVo.setProductName(product.getProductName());
            productVo.setSpecification(product.getSpecification());
            productVo.setSalePrice(product.getSalePrice());
            productVo.setSaleCount(orderCount);
            productVo.setDescription(product.getDescription());
            productVo.setEvaluationList(evaluationList);
            productVo.setEvaluationCount(evaluationList.size());
            productVo.setImageList(productImageList);
            productVo.setSpecificationsList(specificationsList);
            productVo.setMerchantId(product.getMerchantId());
            productVo.setMerchantName(merchantInfo.getMerchantName());
            productVo.setMerchantIcon(merchantInfo.getMerchantIcon());
            productVo.setLocation(merchantInfo.getAddress());


        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取商品信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setData(productVo);
        rsInfo.setMessage("获取商品成功！");
        return rsInfo;
    }

    @RequestMapping(value = "/product/getProductName/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getProductName(@PathVariable("productId") Integer productId) {
        ResultInfo rsInfo = new ResultInfo();
        Product product;
        try {
            //查询商品信息
            product = productService.getProductByProductId(productId);

            if (product == null) {
                rsInfo.setMessage("商品信息不存在！");
                rsInfo.setData(new Product());
                rsInfo.setTotal(0);
                return rsInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("获取商品信息失败！");
            rsInfo.setCode("10001");
            return rsInfo;
        }
        rsInfo.setData(product);
        rsInfo.setMessage("获取商品成功！");
        return rsInfo;
    }
}
