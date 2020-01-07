package com.miao.api.controller;

import com.alibaba.fastjson.JSON;
import com.miao.common.utils.ResultInfo;
import com.miao.common.utils.StringUtil;
import com.miao.pojo.Movie;
import com.miao.service.CarouselService;
import com.miao.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 小程序首页Controller
 *
 * @author miao
 */
@Slf4j
@RestController
@RequestMapping(value = "/index/")
@Api(value = "小程序首页接口", tags = {"小程序首页接口"})
public class IndexController extends BaseController {
    @Resource
    private CarouselService carouselService;
    @Resource
    private MovieService movieService;

    @RequestMapping(value = "carousel/list", method = RequestMethod.GET)
    @ApiOperation(value = "小程序首页轮播图接口", notes = "小程序首页轮播图接口", httpMethod = "GET")
    public ResultInfo getCarouselList() {
        return ResultInfo.success(carouselService.getCarouselList(), "获取首页轮播图成功");

    }

    @RequestMapping(value = "movie/hot", method = RequestMethod.GET)
    @ApiOperation(value = "小程序首页热门超英/热门预告接口", notes = "小程序首页热门超英/热门预告接口", httpMethod = "GET")
    public ResultInfo queryMovie(
            @ApiParam(name = "type", value = "热门超英(superhero)/热门预告(trailer)", required = true)
            @RequestParam("type") String type) {
        List<Movie> hotSuperHero = movieService.getHotSuperHero(type);

        if (StringUtil.isEmpty(type)) {
            return ResultInfo.paramsError("类型不能为空");
        }
        return ResultInfo.success(hotSuperHero, "获取热门超英/预告接口成功");
    }

    @RequestMapping(value = "guessULike", method = RequestMethod.GET)
    @ApiOperation(value = "小程序首页猜你喜欢", notes = "小程序首页猜你喜欢", httpMethod = "GET")
    public ResultInfo guessULike() {
        //1. 获取数据库中movie表所有数量 counts
        int count = movieService.queryAllTrailerCounts();
        //2. 根据counts获取随机的五个数据
        Integer[] guessULikeArray = getGuessULikeArray(count);

        //3. 从Redis中获取数据库中获取具体的movie数据
        List<Movie> movieList = new ArrayList<>();
        for (Integer index : guessULikeArray) {
            String jsonTrailer = redisOperator.get("guess-trailer-id:" + index);
            Movie trailer = JSON.parseObject(jsonTrailer, Movie.class);
            movieList.add(trailer);
        }
        return ResultInfo.success(movieList, "小程序首页猜你喜欢");
    }


}
