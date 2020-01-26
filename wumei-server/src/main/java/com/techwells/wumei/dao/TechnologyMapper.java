package com.techwells.wumei.dao;

import com.techwells.wumei.domain.Technology;
import com.techwells.wumei.domain.rs.RsTechnology;
import com.techwells.wumei.domain.vo.HotTechnologyVo;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author miao
 */
public interface TechnologyMapper {
    /**
     * 通过主键删除技术人员
     *
     * @param userId 主键
     * @return int
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * 插入技术人员信息
     *
     * @param technology 技术人员信息
     * @return int
     */
    int insert(Technology technology);

    /**
     * 有选择的插入技术人员信息（只插入有值的字段）
     *
     * @param technology 技术人员信息
     * @return int
     */
    int insertSelective(Technology technology);

    /**
     * 通过主键查找技术人员信息
     *
     * @param userId 主键
     * @return Technology
     */
    Technology selectByPrimaryKey(Integer userId);

    /**
     * 通过主键有选择的更新技术人员信息
     *
     * @param technology 技术人员信息
     * @return int
     */
    int updateByPrimaryKeySelective(Technology technology);

    /***
     * 通过主键更新技术人员信息
     * @param technology 技术人员信息
     * @return int
     */
    int updateByPrimaryKey(Technology technology);

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
     * @param pageTool 分页
     * @return List
     */
    List<HotTechnologyVo> getHotTechnologyList(PagingTool pageTool);

    /**
     * 逻辑删除技术人员
     *
     * @param userId 主键
     * @return int
     */
    int deleteTechnology(@Param("userId") Integer userId);

    /**
     * 批量逻辑删除技术人员
     *
     * @param userIdArrays 主键数组
     * @return int
     */
    int batchDeleteTechnology(@Param("idArrays") String[] userIdArrays);

    /**
     * 批量审核技术人员
     *
     * @param userIdArrays 技术人员id数组
     * @param status       状态
     * @return int
     */
    int batchEditTechnologyStatus(@Param("idArrays") String[] userIdArrays, @Param("activated") Integer status);

    /**
     * 通过用户id获取技术人员信息
     *
     * @param userId 用户id
     * @return RsTechnology
     */
    RsTechnology getTechnologyInfo(@Param("userId") Integer userId);

    /**
     * 批量修改技术人员推荐
     *
     * @param technologyIdArrays 技术人员id数组
     * @param isRecommend        推荐状态
     * @return int
     */
    int batchEditTechnologyRecommend(@Param("idArrays") String[] technologyIdArrays, @Param("isRecommend") int isRecommend);
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
    List<HotTechnologyVo> getCollectTechnologyList(@Param("list") List<Integer> relationIdList,
                                                   @Param("pageTool") PagingTool pageTool);
}