package com.miao.service.impl;

import com.github.pagehelper.PageHelper;
import com.miao.common.utils.MovieEnum;
import com.miao.mapper.MovieMapper;
import com.miao.pojo.Movie;
import com.miao.service.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 电影预告实现类
 *
 * @author miao
 */
@Service
public class MovieServiceImpl implements MovieService {
    @Resource
    private MovieMapper movieMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Movie> getHotSuperHero(String type) {
        //定义分页，永远拿第一页的数据，第一页前八/前二条记录
        int page = 1;
        int pageSize;

        Example example = new Example(Movie.class);

        if (type.equals(MovieEnum.SUPERHERO.getType())) {
            example.orderBy("score").desc();
            pageSize = 8;
        } else if (type.equals(MovieEnum.TRAILER.getType())) {
            example.orderBy("prisedCounts").desc();
            pageSize = 4;
        } else {
            return null;
        }
        PageHelper.startPage(page, pageSize);


        return movieMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int queryAllTrailerCounts() {

        return movieMapper.selectCount(new Movie());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Movie> getAllTrailerList() {
        return movieMapper.selectAll();
    }
}
