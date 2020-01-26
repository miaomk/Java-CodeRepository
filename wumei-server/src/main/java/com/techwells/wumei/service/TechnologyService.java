package com.techwells.wumei.service;

import com.techwells.wumei.domain.Technology;
import com.techwells.wumei.domain.rs.RsTechnology;
import com.techwells.wumei.domain.vo.HotTechnologyVo;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * @author miao
 */
public interface TechnologyService {
    /**
     * 增加技术人员
     *
     * @param technology 技术人员实体类
     * @return int
     */
    int addTechnology(Technology technology);

    /**
     * 删除技术人员
     *
     * @param userId 用户id
     * @return int
     */
    int deleteTechnology(Integer userId);

    /**
     * 批量删除技术人员
     *
     * @param idArrays 用户id数字
     * @return int
     */
    int batchDeleteTechnology(String[] idArrays);

    /**
     * 修改技术人员信息
     *
     * @param technology 技术人员实体类
     * @return int
     */
    int editTechnologyInfo(Technology technology);

    /**
     * 审核技术人员
     *
     * @param technologyId 技术人员id
     * @param status       状态
     * @return int
     */
    int editTechnologyStatus(Integer technologyId, Integer status);

    /**
     * 批量审核技术人员
     *
     * @param userIds 技术人员id数组
     * @param status  修改状态
     * @return int
     */
    int batchEditTechnologyStatus(String[] userIds, Integer status);

    /**
     * 获取技术人员列表
     *
     * @param pageTool 参数
     * @return List
     */
    List<RsTechnology> getTechnologyList(PagingTool pageTool);

    /**
     * 获取热门大师列表
     *
     * @param pageTool 获取热门大师列表
     * @return List
     */
    List<HotTechnologyVo> getHotTechnologyList(PagingTool pageTool);

    /**
     * 根据用户id获取技术人员详情
     *
     * @param userId 用户id
     * @return RsTechnology
     */
    RsTechnology getTechnologyInfo(Integer userId);

    /**
     * 通过技术人员类型id获取技术人员信息
     *
     * @param technologyId 技术人员类型id
     * @return TechnologyType
     */
    Technology getTechnologyByTechnologyId(int technologyId);

    /**
     * 修改技术人员推荐状态
     *
     * @param userId      技术人员id
     * @param isRecommend 推荐状态
     * @return int
     */
    int modifyRecommend(String userId, int isRecommend);

    /**
     * 查询技术人员数量
     *
     * @param pageTool 分页
     * @return int
     */
    int countTotal(PagingTool pageTool);

    /**
     * 查询热门大师总数
     *
     * @param pageTool 分页
     * @return int
     */
    int hotCountTotal(PagingTool pageTool);

    /**
     * 查询收藏的大师列表
     *
     * @param relationIdList 大师id列表
     * @param pageTool       分页
     * @return List
     */
    List<HotTechnologyVo> getCollectTechnologyList(List<Integer> relationIdList, PagingTool pageTool);
}
