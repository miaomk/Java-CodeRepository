package com.techwells.wumei.controller;

import com.techwells.wumei.service.*;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Controller
public class HomeController {

    @Resource
    private TechnologyService technologyService;

    @Resource
    private ActivityService activityService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private ProductService productService;

    @Resource
    private JoinService joinService;

    /**
     * 首页
     *
     * @return ResultInfo
     */
    @RequestMapping(value = "/home/getHomeDetail")
    @ResponseBody
    public ResultInfo getHomeDetail(@RequestParam("city") String city) {
        ResultInfo rsInfo = new ResultInfo();

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        //热门大师
        Map<String, Object> map = new HashMap<>(12);
        map.put("city", city);
        PagingTool pagingTool = new PagingTool(1, 3);
        pagingTool.setParams(map);
        //热门大师
        Callable<List> technologyListCallable = () -> technologyService.getHotTechnologyList(pagingTool);
        //热门活动
        Callable<List> activityListCallable = () -> activityService.getPopularActivityList(pagingTool);


        FutureTask<List> technologyTask = new FutureTask<>(technologyListCallable);
        FutureTask<List> activityTask = new FutureTask<>(activityListCallable);

        executorService.submit(technologyTask);
        executorService.submit(activityTask);

        Map<String, Object> entity = new HashMap<>(16);
        try {

            entity.put("technologyTask", technologyTask.get());
            entity.put("activityTask", activityTask.get());
        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("首页加载异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        rsInfo.setMessage("获取首页成功！");
        rsInfo.setData(entity);
        return rsInfo;
    }


    /**
     * 商城首页
     *
     * @return ResultInfo
     */
    @RequestMapping(value = "/home/getMallHomeDetail")
    @ResponseBody
    public ResultInfo getMallHomeDetail() {
        ResultInfo rsInfo = new ResultInfo();

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        //厂家推荐
        Callable<List> merchantListCallable = () -> merchantService.getRecommendMerchant(new PagingTool(1, 6));
        //好货推荐
        Callable<List> productListCallable = () -> productService.getPopularList(new PagingTool(1, 4));
        //拼团采购
        Callable<List> joinListCallable = () -> joinService.getHomeJoinList(new PagingTool(1, 3));

        FutureTask<List> merchantTask = new FutureTask<>(merchantListCallable);
        FutureTask<List> productTask = new FutureTask<>(productListCallable);
        FutureTask<List> joinTask = new FutureTask<>(joinListCallable);


        executorService.submit(merchantTask);
        executorService.submit(productTask);
        executorService.submit(joinTask);

        Map<String, Object> entity = new HashMap<>(16);
        try {

            entity.put("merchantTask", merchantTask.get());
            entity.put("productTask", productTask.get());
            entity.put("joinTask", joinTask.get());

        } catch (Exception e) {
            e.printStackTrace();
            rsInfo.setMessage("商城首页加载异常！");
            rsInfo.setCode("10001");
            return rsInfo;
        }

        rsInfo.setMessage("获取首页成功！");
        rsInfo.setData(entity);
        return rsInfo;
    }


}
