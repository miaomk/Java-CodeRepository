package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.CollectMapper;
import com.techwells.wumei.dao.CommodityMapper;
import com.techwells.wumei.domain.Commodity;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.domain.rs.RsCommodity;
import com.techwells.wumei.service.CommodityService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miao
 */
@Service(value = "CommodityService")
public class CommodityServiceImpl implements CommodityService {

    @Resource
    private CommodityMapper commodityMapper;

    @Resource
    private CollectMapper collectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addCommodity(Commodity commodity) {
        int count;
        try {

            count = commodityMapper.insertSelective(commodity);
            if (count < 0) {
                throw new Exception("增加租赁商品失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("增加租赁商品异常");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCommodity(String commodityId) {
        int count;
        try {
            String arrayFlag = ",";
            if (commodityId.contains(arrayFlag)) {
                String[] commodityIdArrays = commodityId.split(",");
                count = commodityMapper.batchDelete(commodityIdArrays);

            } else {

                count = commodityMapper.deleteCommodity(commodityId);
            }

            if (count < 0) {
                throw new Exception("删除租赁商品失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除租赁商品异常");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyCommodity(Commodity commodity) {
        int count;
        try {

            count = commodityMapper.updateByPrimaryKeySelective(commodity);
            if (count < 0) {
                throw new Exception("修改租赁商品失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改租赁商品异常");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyCommodityStatus(String commodityId, String activated) {
        int count;
        try {
            String arrayFlag = ",";
            if (commodityId.contains(arrayFlag)) {

                String[] commodityIdArrays = commodityId.split(",");
                count = commodityMapper.batchUpdateCommodityStatus(commodityIdArrays, activated);
            } else {
                Commodity commodity = new Commodity();
                commodity.setCommodityId(Integer.parseInt(commodityId));
                commodity.setActivated(Integer.parseInt(activated));

                count = commodityMapper.updateByPrimaryKeySelective(commodity);
            }

            if (count < 0) {
                throw new Exception("删除租赁商品失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除租赁商品异常");
        }

        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RsCommodity> getCommodityList(PagingTool pageTool) {
        List<RsCommodity> rsCommodities;
        try {

            rsCommodities = commodityMapper.getCommodityList(pageTool);
            //处理商品展示图片
            for (RsCommodity rsCommodity : rsCommodities) {
                String commodityIcon = rsCommodity.getCommodityIcon();
                rsCommodity.setCommodityIcon(commodityIcon.split(",")[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取严选租赁商品列表异常");
        }
        return rsCommodities;
    }

    @Override
    @Transactional(readOnly = true)
    public RsCommodity getCommodityInfoById(String commodityId, String userId) {
        RsCommodity commodityInfo;
        try {
            //查询租赁商品详情
            commodityInfo = commodityMapper.getCommodityInfo(commodityId);
            //查询推荐
            Integer collectId = collectMapper.getCollectId(commodityId, userId, 3);
            commodityInfo.setCollectId(collectId);

        } catch (Exception e) {

            throw new RuntimeException("获取商品信息异常");
        }
        return commodityInfo;
    }

    @Override
    public int countTotal(PagingTool pageTool) {
        int count;

        try {
            count = commodityMapper.countTotal(pageTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取活动异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editCommodityRecommend(String commodityId, String recommend) {
        int count;
        try {
            String arrayFlag = ",";
            if (commodityId.contains(arrayFlag)) {

                String[] commodityIdArrays = commodityId.split(",");
                count = commodityMapper.batchUpdateCommodityStatus(commodityIdArrays, recommend);
            } else {
                Commodity commodity = new Commodity();
                commodity.setCommodityId(Integer.parseInt(commodityId));
                commodity.setRecommend(Integer.parseInt(recommend));

                count = commodityMapper.updateByPrimaryKeySelective(commodity);
            }

            if (count < 0) {
                throw new Exception("删除租赁商品失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除租赁商品异常");
        }

        return count;
    }

    @Override
    public List<RsCollect> getCollectCommodityList(List<Integer> relationIdList, PagingTool pageTool) {
        List<RsCollect> collectCommodityList;
        try {

            collectCommodityList = commodityMapper.getCollectCommodityList(relationIdList, pageTool);
            for (RsCollect rsCollect : collectCommodityList) {
                if(null != rsCollect.getCommodityIcon()){
                    String commodityIcon = rsCollect.getCommodityIcon().split(",")[0];
                    rsCollect.setCommodityIcon(commodityIcon);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取严选租赁商品列表异常");
        }
        return collectCommodityList;
    }
}
