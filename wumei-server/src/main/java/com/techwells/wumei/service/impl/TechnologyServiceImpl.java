package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.TechnologyCaseMapper;
import com.techwells.wumei.dao.TechnologyEvaluateMapper;
import com.techwells.wumei.dao.TechnologyMapper;
import com.techwells.wumei.dao.UserMapper;
import com.techwells.wumei.domain.Technology;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.rs.RsTechnology;
import com.techwells.wumei.domain.vo.HotTechnologyVo;
import com.techwells.wumei.domain.vo.TechnologyCaseVO;
import com.techwells.wumei.domain.vo.TechnologyEvaluateVO;
import com.techwells.wumei.service.TechnologyService;
import com.techwells.wumei.util.PagingTool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techwells.wumei.util.ConstantUtil.IMAGESHOWSIZE;

/**
 * 大师实现类
 *
 * @author miao
 */
@Service("TechnologyService")
public class TechnologyServiceImpl implements TechnologyService {


    @Resource
    private TechnologyMapper technologyMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TechnologyEvaluateMapper evaluateMapper;
    @Resource
    private TechnologyCaseMapper caseMapper;


    private static final String SPLIT = ",";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addTechnology(Technology technology) {
        int count;
        try {

            count = technologyMapper.insertSelective(technology);

            if (count < 0) {
                throw new Exception("添加大师失败!");
            }

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("添加大师异常!");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteTechnology(Integer userId) {
        int count;
        try {
            count = technologyMapper.deleteTechnology(userId);
            if (count < 0) {
                throw new Exception("删除大师失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除大师异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeleteTechnology(String[] idArrays) {
        int count;
        try {
            count = technologyMapper.batchDeleteTechnology(idArrays);
            if (count < 0) {
                throw new Exception("批量删除大师失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("批量删除大师异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editTechnologyInfo(Technology technology) {
        int count;
        try {
            //更新大师信息
            count = technologyMapper.updateByPrimaryKeySelective(technology);
            //更新用户信息
            if (null != technology.getTechnologyAge()) {
                User user = new User();
                user.setUserId(technology.getUserId());
                user.setAge(technology.getTechnologyAge());
                userMapper.updateByPrimaryKeySelective(user);
            }
            if (count < 0) {
                throw new Exception("修改大师信息失败!");
            }

        } catch (Exception e) {

            e.printStackTrace();
            throw new ApplicationContextException("修改大师信息异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editTechnologyStatus(Integer technologyId, Integer status) {
        int count;
        try {

            Technology technology = new Technology();

            technology.setUserId(technologyId);
            technology.setActivated(status);

            count = technologyMapper.updateByPrimaryKeySelective(technology);

            if (count < 0) {
                throw new Exception("审核大师信息失败!");
            }

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("审核大师信息异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchEditTechnologyStatus(String[] userIds, Integer status) {
        int count;
        try {

            count = technologyMapper.batchEditTechnologyStatus(userIds, status);
            if (count < 0) {
                throw new Exception("审核大师信息失败!");
            }

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException("审核大师信息异常！");
        }
        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RsTechnology> getTechnologyList(PagingTool pageTool) {
        List<RsTechnology> technologyList;

        try {

            technologyList = technologyMapper.getTechnologyList(pageTool);
            List<HotTechnologyVo> newTechnologyList = new ArrayList<>();
            for (RsTechnology rsTechnology : technologyList) {
                HotTechnologyVo hotTechnologyVo = new HotTechnologyVo();
                BeanUtils.copyProperties(rsTechnology, hotTechnologyVo);
                newTechnologyList.add(hotTechnologyVo);
            }
            if (CollectionUtils.isNotEmpty(technologyList)) {
                transferImage(newTechnologyList);
                for (HotTechnologyVo hotTechnologyVo : newTechnologyList) {
                    for (RsTechnology rsTechnology : technologyList) {
                        if (hotTechnologyVo.getUserId().equals(rsTechnology.getUserId())) {
                            rsTechnology.setCaseImage(hotTechnologyVo.getCaseImage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取大师列表异常");
        }
        return technologyList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotTechnologyVo> getHotTechnologyList(PagingTool pageTool) {
        List<HotTechnologyVo> technologyList;

        try {

            technologyList = technologyMapper.getHotTechnologyList(pageTool);
            if (CollectionUtils.isNotEmpty(technologyList)) {
                transferImage(technologyList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取大师列表异常");
        }
        return technologyList;
    }

    @Override
    @Transactional(readOnly = true)
    public RsTechnology getTechnologyInfo(Integer userId) {
        RsTechnology rsTechnology;
        try {

            rsTechnology = technologyMapper.getTechnologyInfo(userId);
            if (rsTechnology == null) {
                return null;
            }
            //入驻天数
            rsTechnology.setEnterDay((int) Duration.between(rsTechnology.getCreatedDate(), LocalDateTime.now()).toDays());

            PagingTool pagingTool = new PagingTool(1, 1000);
            Map<String, Object> param = new HashMap<>(12);
            param.put("technologyId", userId);
            param.put("activated", 0);
            pagingTool.setParams(param);
            //查询用户评价数
            int evaluateCount = evaluateMapper.countTotal(pagingTool);
            rsTechnology.setEvaluateCount(evaluateCount);

            if (evaluateCount > 0) {
                //查询用户评价列表
                List<TechnologyEvaluateVO> evaluateList = evaluateMapper.getTechnologyEvaluateList(pagingTool);
                rsTechnology.setEvaluateList(evaluateList);
                BigDecimal attitudeScore = BigDecimal.ZERO;
                BigDecimal imageScore = BigDecimal.ZERO;
                BigDecimal effectScore = BigDecimal.ZERO;
                BigDecimal overallScore = BigDecimal.ZERO;

                for (TechnologyEvaluateVO technologyEvaluateVO : evaluateList) {
                    attitudeScore = attitudeScore.add(technologyEvaluateVO.getAttitudeScore());
                    imageScore = imageScore.add(technologyEvaluateVO.getImageScore());
                    effectScore = effectScore.add(technologyEvaluateVO.getEffectScore());
                    overallScore = overallScore.add(technologyEvaluateVO.getOverallScore());
                }
                BigDecimal evaluateDecimal = new BigDecimal(evaluateCount);
                rsTechnology.setAttitudeScore(attitudeScore.divide(evaluateDecimal, 1, BigDecimal.ROUND_HALF_UP));
                rsTechnology.setImageScore(imageScore.divide(evaluateDecimal, 1, BigDecimal.ROUND_HALF_UP));
                rsTechnology.setEffectScore(effectScore.divide(evaluateDecimal, 1, BigDecimal.ROUND_HALF_UP));
                rsTechnology.setOverallScore(overallScore.divide(evaluateDecimal, 1, BigDecimal.ROUND_HALF_UP));

            } else {

                rsTechnology.setAttitudeScore(new BigDecimal(5.0));
                rsTechnology.setImageScore(new BigDecimal(5.0));
                rsTechnology.setEffectScore(new BigDecimal(5.0));
                rsTechnology.setOverallScore(new BigDecimal(5.0));
            }

            //查询案例数
            int caseCount = caseMapper.countTotal(pagingTool);
            rsTechnology.setCaseCount(caseCount);
            if (caseCount > 0) {
                List<TechnologyCaseVO> caseList = caseMapper.getTechnologyCaseList(pagingTool);
                rsTechnology.setCaseList(caseList);
                StringBuilder imageBuilder = new StringBuilder();
                caseList.forEach(data -> {
                    data.setImageCover(data.getImageUrl().split(SPLIT)[0]);
                    imageBuilder.append(data.getImageUrl()).append(SPLIT);
                });
                rsTechnology.setCaseImageCount(imageBuilder.toString().split(SPLIT).length);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取大师列表异常");
        }
        return rsTechnology;
    }

    @Override
    public Technology getTechnologyByTechnologyId(int technologyId) {
        Technology technologyCase;
        try {
            technologyCase = technologyMapper.selectByPrimaryKey(technologyId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取大师详情异常！");
        }
        return technologyCase;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyRecommend(String userId, int isRecommend) {

        int count;
        try {

            if (userId.contains(SPLIT)) {
                String[] technologyIdArrays = userId.split(SPLIT);
                //批量修改大师推荐状态
                count = technologyMapper.batchEditTechnologyRecommend(technologyIdArrays, isRecommend);

            } else {
                Technology technology = new Technology();
                technology.setUserId(Integer.parseInt(userId));
                technology.setIsRecommend(isRecommend);
                //修改大师推荐状态
                count = technologyMapper.updateByPrimaryKeySelective(technology);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改大师推荐状态异常");
        }


        return count;
    }

    @Override
    public int countTotal(PagingTool pageTool) {
        int count;

        try {
            count = technologyMapper.countTotal(pageTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取大师数量异常！");
        }
        return count;
    }

    @Override
    public int hotCountTotal(PagingTool pageTool) {
        int count;

        try {
            count = technologyMapper.hotCountTotal(pageTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取热门大师数量异常！");
        }
        return count;
    }

    @Override
    public List<HotTechnologyVo> getCollectTechnologyList(List<Integer> relationIdList, PagingTool pageTool) {
        List<HotTechnologyVo> technologyList;
        try {

            technologyList = technologyMapper.getCollectTechnologyList(relationIdList, pageTool);
            if (CollectionUtils.isNotEmpty(technologyList)) {
                transferImage(technologyList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取收藏大师列表异常");
        }
        return technologyList;
    }


    private void transferImage(List<HotTechnologyVo> technologyList) {
        technologyList.forEach(data -> {
            if (CollectionUtils.isNotEmpty(data.getCaseList())) {
                StringBuilder caseImageBuilder = new StringBuilder();
                data.getCaseList().forEach(caseData -> {
                    //拼接案例图片
                    caseImageBuilder.append(caseData.getImageUrl()).append(SPLIT);
                });
                //有几个案例图
                String[] imageArray = caseImageBuilder.toString().split(SPLIT);
                StringBuilder imageBuilder = new StringBuilder();
                if (imageArray.length >= IMAGESHOWSIZE) {
                    for (int i = 0; i < IMAGESHOWSIZE; i++) {
                        imageBuilder.append(imageArray[i]).append(SPLIT);
                    }
                } else {
                    for (String s : imageArray) {
                        imageBuilder.append(s).append(SPLIT);
                    }
                }
                String caseImage = imageBuilder.toString().substring(0, imageBuilder.length() - 1);
                data.setCaseImage(caseImage.split(SPLIT));
            }
        });
    }
}
