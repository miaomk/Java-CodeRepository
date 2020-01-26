package com.techwells.wumei.service.impl;

import java.util.List;

import com.techwells.wumei.domain.PopularProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.ProductMapper;
import com.techwells.wumei.domain.Product;
import com.techwells.wumei.service.ProductService;
import com.techwells.wumei.util.PagingTool;


@Service("ProductService")
public class ProductServiceImpl implements ProductService {

    private ProductMapper productMapper;

    public ProductMapper getProductMapper() {
        return productMapper;
    }

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public int addProduct(Product product) {
        int count = 0;
        try {
            count = productMapper.insertSelective(product);
            if (count < 0) {
                throw new Exception("添加模板失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加模板异常！");
        }
        return count;
    }


    @Override
    public int delProduct(int productId, String deleteStatus) {
        int count = 0;
        try {
            //count = productMapper.deleteByPrimaryKey(productId);
            //更新删除
            Product product = new Product();
            product.setProductId(productId);
            Boolean deleted = "1".equals(deleteStatus);

            product.setDeleted(deleted);

            count = productMapper.updateByPrimaryKeySelective(product);
            if (count < 0) {
                throw new Exception("删除模板失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除模板异常！");
        }
        return count;
    }

    @Override
    public int modifyProduct(Product product) {
        int count = 0;
        try {
            count = productMapper.updateByPrimaryKeySelective(product);
            if (count < 0) {
                throw new Exception("修改模板失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改模板异常！");
        }
        return count;
    }

    @Override
    public Product getProductByProductId(int productId) {
        Product product;
        try {
            product = productMapper.selectByPrimaryKey(productId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取模板详情异常！");
        }
        return product;
    }

    // 获取列表
    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = productMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取模板总数异常！");
        }
        return count;
    }

    @Override
    public List<Product> getProductList(PagingTool pagingTool) {
        List<Product> productList;

        try {
            productList = productMapper.selectProductList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取模板列表异常");
        }
        return productList;
    }

    @Override
    public int deleteBatch(String[] idArr) {
        int count;

        try {
            count = productMapper.batchDelete(idArr);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量删除商品异常！");
        }
        return count;
    }

    @Override
    public int batchUpdateIsOnSale(String[] productIdArray, String status) {
        int count;

        try {
            count = productMapper.batchUpdateActivated(productIdArray, status);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量更新上下架异常！");
        }
        return count;
    }

    @Override
    public List<PopularProductVO> getPopularList(PagingTool pageTool) {
        List<PopularProductVO> productList;

        try {

            productList = productMapper.getPopularList(pageTool);
            for (PopularProductVO popularProductVO : productList) {
                if (null != popularProductVO.getProductIcon()) {

                    String productIcon = popularProductVO.getProductIcon().split(",")[0];
                    popularProductVO.setProductIcon(productIcon);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取好货推荐异常");
        }
        return productList;
    }

    @Override
    public int popularProductCountTotal(PagingTool pageTool) {
        int count;

        try {

            count = productMapper.popularProductCount(pageTool);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取好货推荐总数异常！");
        }
        return count;
    }

}
