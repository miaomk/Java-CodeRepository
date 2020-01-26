package com.techwells.wumei.service;

import com.techwells.wumei.domain.Activity;
import com.techwells.wumei.domain.CompanyActivityInfo;
import com.techwells.wumei.domain.rs.RsActivityManage;
import com.techwells.wumei.domain.rs.RsActivity;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * @author miao
 */
public interface ActivityService {
    /**
     * 增加活动
     *
     * @param activity 活动实体类
     * @return int
     */
    int addActivity(Activity activity);

    /**
     * 删除活动
     *
     * @param activityId 活动id
     * @return int
     */
    int deleteActivity(int activityId);

    /**
     * 批量删除活动
     *
     * @param activityIdArrays 活动id数组
     * @return int
     */
    int batchDeleteActivity(String[] activityIdArrays);

    /**
     * 修改活动信息
     *
     * @param activity 活动信息
     * @return int
     */
    int modifyActivity(Activity activity);

    /**
     * 获取活动列表
     *
     * @param pageTool 参数
     * @return list
     */
    List<Activity> getActivityList(PagingTool pageTool);

    /**
     * 查询公司名下所有的活动列表
     *
     * @param companyId 公司id
     * @return List
     */
    List<RsActivity> getActivityByCompanyId(Integer companyId);

    /**
     * 查询热门活动列表
     *
     * @param pageTool 分页
     * @return List
     */
    List<RsActivity> getPopularActivityList(PagingTool pageTool);

    /**
     * 根据活动id查询活动详情
     *
     * @param activityId 活动id
     * @param userId     用户id
     * @return RsActivity
     */
    RsActivity getActivityInfo(String activityId, String userId);

    /**
     * 查询公司名下活动列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<RsActivityManage> getCompanyActivityList(PagingTool pagingTool);

    /**
     * 通过活动id获取活动信息
     *
     * @param activityId 活动id
     * @return CompanyActivityInfo
     */
    CompanyActivityInfo getCompanyActivityInfo(String activityId);

    int countTotal(PagingTool pageTool);

    int popularCountTotal(PagingTool pageTool);

    /**
     * 根据活动id查询活动详细信息
     *
     * @param activityId 活动id
     * @return RsActivity
     */
    Activity queryActivityInfo(String activityId);

    /**
     * 查询公司名下活动总数
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
    int batchUpdateActivity(List<Activity> activityList);

    /**
     * 获取收藏的活动列表
     *
     * @param relationIdList 被收藏的活动id列表
     * @param pageTool       分页
     * @return List
     */
    List<RsCollect> getCollectActivityList(List<Integer> relationIdList, PagingTool pageTool);
}
