package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.BrandMapper;
import com.techwells.wumei.domain.Brand;
import com.techwells.wumei.service.BrandService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 轮播图业务实现类
 *
 * @author Administrator
 */
@Service("BrandService")
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    @Override
    public int addBrand(Brand brand) {
        int count;
        try {
            count = brandMapper.insertSelective(brand);
            if (count < 0) {
                throw new Exception("添加轮播图失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加轮播图异常");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delBrand(int brandId) {
        int count;
        try {
            count = brandMapper.deleteByPrimaryKey(brandId);
            if (count < 0) {
                throw new Exception("删除轮播图失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除轮播图异常");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyBrand(Brand brand) {
        int count;
        try {
            count = brandMapper.updateByPrimaryKeySelective(brand);
            if (count < 0) {
                throw new Exception("修改轮播图失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改轮播图异常");
        }
        return count;
    }

    @Override
    public Brand getBrandByBrandId(int brandId) {
        Brand brand;
        try {
            brand = brandMapper.selectByPrimaryKey(brandId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取轮播图详情异常！");
        }
        return brand;
    }


    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = brandMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取轮播图总数异常！");
        }
        return count;
    }

    @Override
    public List<Brand> getBrandList(PagingTool pagingTool) {
        List<Brand> brandList;

        try {
            brandList = brandMapper.selectBrandList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取轮播图列表异常");
        }
        return brandList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String[] idArr) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStartBrand() {
		int count;

		try {
			count = brandMapper.updateStartBrand();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("更新轮播图结束状态异常！");
		}
		return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateEndBrand() {
		int count;

		try {
			count = brandMapper.updateEndBrand();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("更新轮播图启用状态异常！");
		}
		return count;
    }

}
