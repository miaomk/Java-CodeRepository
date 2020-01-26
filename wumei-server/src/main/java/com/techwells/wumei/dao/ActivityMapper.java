package com.techwells.wumei.dao;

import com.techwells.wumei.domain.Activity;
import com.techwells.wumei.domain.CompanyActivityInfo;
import com.techwells.wumei.domain.rs.RsActivityManage;
import com.techwells.wumei.domain.rs.RsActivity;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityMapper {
    int deleteByPrimaryKey(Integer activityId);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer activityId);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    /**
     * 逻辑删除活动
     *
     * @param activityId 活动id
     * @return int
     */
    int deleteActivity(Integer activityId);

    /**
     * 批量逻辑删除活动
     *
     * @param activityIdArrays 活动id数组
     * @return int
     */
    int batchDeleteActivity(@Param("ids") String[] activityIdArrays);

    /**
     * 获取活动列表
     *
     * @param pageTool 参数
     * @return list
     */
    List<Activity> getActivityList(PagingTool pageTool);

    /**
     * 获取公司待举办的活动数
     *
     * @param companyId 公司id
     * @return int
     */
    int getActivityCount(Integer companyId);

    /**
     * 查询公司名下待举办的活动
     *
     * @param companyId 公司id
     * @return List
     */
    List<RsActivity> getRsActivityList(Integer companyId);

    /**
     * 查询热门活动列表
     *
     * @param pageTool 分页
     * @return List
     */
    List<RsActivity> getPopularActivityList(PagingTool pageTool);

    /**
     * 查询公司下活动列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<RsActivityManage> getCompanyActivityList(PagingTool pagingTool);

    /**
     * 查询公司活动详细信息
     *
     * @param activityId 活动id
     * @return CompanyActivityInfo
     */
    CompanyActivityInfo getCompanyActivityInfo(@Param("activityId") String activityId);

    int countTotal(PagingTool pagingTool);

    /**
     * 热门活动总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int popularCountTotal(PagingTool pagingTool);

    /**
     * 查询公司下活动总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int getCompanyActivityCount(PagingTool pagingTool);

    /**
     * 批量更新活动列表
     *
     * @param activityList 活动列表
     * @return int
     */
    int batchUpdateActivity(@Param("list") List<Activity> activityList);

    /**
     * 获取收藏的活动列表
     *
     * @param relationIdList 被收藏的活动id列表
     * @param pageTool       分页
     * @return List
     */
    List<RsCollect> getCollectActivityList(@Param("list") List<Integer> relationIdList,
                                           @Param("pageTool") PagingTool pageTool);
}