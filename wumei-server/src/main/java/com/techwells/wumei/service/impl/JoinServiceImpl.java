package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.EvaluationMapper;
import com.techwells.wumei.dao.JoinMapper;
import com.techwells.wumei.dao.MerchantMapper;
import com.techwells.wumei.domain.Join;
import com.techwells.wumei.domain.Merchant;
import com.techwells.wumei.domain.rs.RsEvaluation;
import com.techwells.wumei.domain.rs.RsJoin;
import com.techwells.wumei.service.JoinService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_UP;


@Service("JoinService")
public class JoinServiceImpl implements JoinService {
    @Resource
    private JoinMapper joinMapper;
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MerchantMapper merchantMapper;

    @Override
    public int addJoin(Join join) {
        int count;
        try {
            count = joinMapper.insertSelective(join);
            if (count < 0) {
                throw new Exception("添加拼团商品失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加拼团商品异常！");
        }
        return count;
    }

    @Override
    public int delJoin(int joinId) {
        int count;
        try {
            count = joinMapper.deleteByPrimaryKey(joinId);
            if (count < 0) {
                throw new Exception("删除拼团商品失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除拼团商品异常！");
        }
        return count;
    }

    @Override
    public int modifyJoin(Join join) {
        int count;
        try {
            count = joinMapper.updateByPrimaryKeySelective(join);
            if (count < 0) {
                throw new Exception("修改拼团商品失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改拼团商品异常！");
        }
        return count;
    }

    @Override
    public RsJoin getJoinByJoinId(int joinId) {
        RsJoin join;
        try {
            join = joinMapper.selectJoinById(joinId);
            //查询店铺信息
            Merchant merchant = merchantMapper.selectByPrimaryKey(join.getMerchantId());

            //查询好评率
            PagingTool pagingTool = new PagingTool(1, 9999);
            Map<String, Object> map = new HashMap<>(12);
            map.put("merchantId", join.getMerchantId());
            map.put("productId", join.getProductId());
            pagingTool.setParams(map);
            List<RsEvaluation> merchantEvaluationList = evaluationMapper.getMerchantEvaluationList(pagingTool);
            int goodCount = (int) merchantEvaluationList.stream().filter(data -> data.getEvaluationLevel() == 1).count();
            BigDecimal goodRate = new BigDecimal(goodCount).divide
                    (new BigDecimal(merchantEvaluationList.size()), 2, ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));

            //查找其他拼团
            List<RsJoin> joinList = joinMapper.selectJoinList(pagingTool);
            if (joinList.size() > 1) {
                List<RsJoin> collectList = joinList.stream().filter(data -> data.getJoinId() != joinId).collect(Collectors.toList());
                join.setJoinList(collectList);
            }

            //查询商品全部评价
            List<RsEvaluation> rsEvaluations = evaluationMapper.selectEvaluationList(pagingTool);

            join.setMerchantName(merchant.getMerchantName());
            join.setMerchantId(merchant.getUserId());
            join.setMerchantLogo(merchant.getMerchantLogo());
            join.setPraiseRate(goodRate.intValue());
            join.setEvaluations(rsEvaluations);
            join.setEvaluationCount(rsEvaluations.size());

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取拼团商品详情异常！");
        }
        return join;
    }

    // 获取列表
    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = joinMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取拼团商品总数异常！");
        }
        return count;
    }

    @Override
    public List<RsJoin> getJoinList(PagingTool pagingTool) {
        List<RsJoin> joinList;

        try {
            joinList = joinMapper.selectJoinList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取拼团商品列表异常");
        }
        return joinList;
    }

    @Override
    public List<RsJoin> getHomeJoinList(PagingTool pagingTool) {
        List<RsJoin> joinList;

        try {
            joinList = joinMapper.getHomeJoinList(pagingTool);
			for (RsJoin rsJoin : joinList) {
				if(null != rsJoin.getProductIcon()){
					String productIcon = rsJoin.getProductIcon().split(",")[0];
					rsJoin.setProductIcon(productIcon);
				}
			}
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取商城首页拼团商品列表异常");
        }
        return joinList;
    }

    @Override
    public int deleteBatch(String[] idArr) {
        // TODO Auto-generated method stub
        return 0;
    }

}
