package com.miao.service.impl;

import com.miao.mapper.CarouselMapper;
import com.miao.pojo.Carousel;
import com.miao.service.CarouselService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import java.util.List;

/**
 * 轮播图业务实现类
 *
 * @author miao
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Carousel> getCarouselList() {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").desc();
        Criteria carousel = example.createCriteria();
        carousel.andEqualTo("isShow", 1);
        List<Carousel> carouselList = carouselMapper.selectByExample(example);
        return carouselList;
    }
}
