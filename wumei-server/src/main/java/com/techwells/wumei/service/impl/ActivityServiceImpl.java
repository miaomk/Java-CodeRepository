package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.*;
import com.techwells.wumei.domain.Activity;
import com.techwells.wumei.domain.ActivityForm;
import com.techwells.wumei.domain.CompanyActivityInfo;
import com.techwells.wumei.domain.Ticket;
import com.techwells.wumei.domain.rs.RsActivity;
import com.techwells.wumei.domain.rs.RsActivityManage;
import com.techwells.wumei.domain.rs.RsCollect;
import com.techwells.wumei.domain.rs.RsCompany;
import com.techwells.wumei.service.ActivityService;
import com.techwells.wumei.util.DateUtil;
import com.techwells.wumei.util.PagingTool;
import com.techwells.wumei.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 活动实现类
 *
 * @author miao
 */
@Service("ActivityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private CompanyMapper companyMapper;
    @Resource
    private FocusMapper focusMapper;
    @Resource
    private TicketMapper ticketMapper;
    @Resource
    private ActivityFormMapper activityFormMapper;
    @Resource
    private ActivityOrderMapper activityOrderMapper;
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private PVMapper pvMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addActivity(Activity activity) {

        int count;
        try {

            Ticket ticket = activity.getTicket();
            if (null == ticket) {
                activity.setActivityFree(true);
                activity.setActivityFee((double) 0);

            } else {
                activity.setActivityFree(false);
                activity.setActivityFee(ticket.getTicketFee());
            }

            count = activityMapper.insertSelective(activity);
            if (count < 0) {
                throw new Exception("添加活动失败!");
            }
            //插入活动门票
            if (null != ticket) {

                ticket.setActivityId(activity.getActivityId());
                ticketMapper.insertSelective(ticket);
            }
            //插入活动报名表单
            ActivityForm activityForm = new ActivityForm();
            activityForm.setActivityId(activity.getActivityId());
            activityForm.setContent(activity.getForm());
            activityFormMapper.insertSelective(activityForm);

        } catch (Exception e) {

            e.printStackTrace();
            throw new ApplicationContextException("添加活动异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteActivity(int activityId) {
        int count;

        try {

            count = activityMapper.deleteActivity(activityId);
            count += ticketMapper.deleteByActivityId(activityId);
            count += activityFormMapper.deleteByPrimaryKey(activityId);

            if (count < 1) {
                throw new Exception("删除活动失败!");
            }

        } catch (Exception e) {

            e.printStackTrace();
            throw new ApplicationContextException("删除活动异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeleteActivity(String[] activityIdArrays) {
        int count;

        try {

            count = activityMapper.batchDeleteActivity(activityIdArrays);
            count += ticketMapper.batchDeleteByActivityId(activityIdArrays);
            count += activityFormMapper.batchDelete(activityIdArrays);

            if (count < 1) {
                throw new Exception("删除活动失败!");
            }

        } catch (Exception e) {

            e.printStackTrace();
            throw new ApplicationContextException("删除活动异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyActivity(Activity activity) {
        int count;
        try {
            //更新活动信息
            count = activityMapper.updateByPrimaryKeySelective(activity);

            //更新活动门票信息
            if (null != activity.getTicket()) {

                count += ticketMapper.updateByPrimaryKeySelective(activity.getTicket());
            }
            //更新活动报名表单信息
            if (!StringUtil.isEmpty(activity.getForm())) {
                ActivityForm activityForm = new ActivityForm();
                activityForm.setActivityId(activity.getActivityId());
                activityForm.setContent(activity.getForm());
                if (StringUtil.isEmpty(activity.getForm())) {
                    activityForm.setDeleted(true);
                }
                count += activityFormMapper.updateByPrimaryKeySelective(activityForm);
            }

            if (count < 1) {
                throw new Exception("修改活动信息失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改活动信息异常");
        }

        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Activity> getActivityList(PagingTool pageTool) {
        List<Activity> activityList;

        try {

            activityList = activityMapper.getActivityList(pageTool);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取活动列表异常");
        }
        return activityList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RsActivity> getActivityByCompanyId(Integer companyId) {

        List<RsActivity> rsActivityList;
        try {

            rsActivityList = activityMapper.getRsActivityList(companyId);

            if (CollectionUtils.isNotEmpty(rsActivityList)) {

                for (RsActivity rsActivity : rsActivityList) {

                    String activityWeek = DateUtil.ActivityWeek(rsActivity.getActivityStartTime());

                    rsActivity.setActivityTime(activityWeek);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取公司活动列表异常");
        }

        return rsActivityList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RsActivity> getPopularActivityList(PagingTool pageTool) {
        List<RsActivity> rsActivityList;

        try {

            rsActivityList = activityMapper.getPopularActivityList(pageTool);

            if (CollectionUtils.isNotEmpty(rsActivityList)) {


                for (RsActivity rsActivity : rsActivityList) {

                    String activityWeek = DateUtil.ActivityWeek(rsActivity.getActivityStartTime());
                    rsActivity.setActivityTime(activityWeek);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取热门活动列表异常");
        }

        return rsActivityList;
    }

    @Override
    @Transactional(readOnly = true)
    public RsActivity getActivityInfo(String activityId, String userId) {
        Activity activity = activityMapper.selectByPrimaryKey(Integer.parseInt(activityId));

        if (null == activity) {

            return null;
        }
        RsActivity rsActivity = new RsActivity();

        try {
            //数据赋值
            rsActivity.setActivityId(activity.getActivityId());
            rsActivity.setCompanyId(activity.getCompanyId());
            rsActivity.setActivityTheme(activity.getActivityTheme());
            rsActivity.setActivityStartTime(activity.getActivityStartTime());
            rsActivity.setActivityEndTime(activity.getActivityEndTime());
            rsActivity.setActivityFee(activity.getActivityFee());
            rsActivity.setActivityFree(activity.getActivityFree());
            rsActivity.setActivityLocation(activity.getActivityLocation());
            rsActivity.setActivityLogo(activity.getActivityLogo());
            rsActivity.setActivityType(activity.getActivityType());
            rsActivity.setActivityIntroduce(activity.getActivityIntroduce());

            //查询活动浏览数
            PagingTool pagingTool = new PagingTool();
            Map<String, Object> map = new HashMap<>(12);
            map.put("relationId", activityId);
            map.put("pvType", 1);
            pagingTool.setParams(map);

            int pvCount = pvMapper.countTotal(pagingTool);
            rsActivity.setActivityPv(pvCount);

            //时间格式处理
            String activityWeek = DateUtil.ActivityWeek(rsActivity.getActivityStartTime());
            rsActivity.setActivityTime(activityWeek);

            RsCompany rsCompany = companyMapper.getCompanyByCompanyId(rsActivity.getCompanyId());
            if (null == rsCompany) {
                return null;
            }
            int fansCount = focusMapper.focusCount(rsActivity.getCompanyId());
            int activityCount = activityMapper.getActivityCount(rsActivity.getCompanyId());
            int focusStatus = focusMapper.queryFocus(Integer.parseInt(userId), rsActivity.getCompanyId(), 1) > 0 ? 1 : 0;
            int collectCount = collectMapper.selectCollectCount(activityId, 2);

            //获取收藏id
            Integer collectId = collectMapper.getCollectId(activityId, userId, 2);
            rsActivity.setCollectId(collectId);

            //获取关注id
            Integer focusId = focusMapper.getFocusId(rsActivity.getCompanyId(), userId, 1);
            rsActivity.setFocusId(focusId);

            //获取票种信息
            Ticket ticket = ticketMapper.getTicketInfoByActivity(activityId);

            //查询剩余票数
            //1.查询已售出活动票数量
            int saleTicketCount = activityOrderMapper.getSaleTicketCount(Integer.parseInt(activityId));
            //2.计算出剩余票数
            int ticketRemain = ticket.getAmount() - saleTicketCount;
            if (ticketRemain < 0) {
                ticket.setAmount(0);
            } else {
                ticket.setAmount(ticketRemain);
            }
            rsActivity.setTicket(ticket);

            //获取报名表单信息
            ActivityForm activityForm = activityFormMapper.selectByPrimaryKey(rsActivity.getActivityId());
            rsActivity.setActivityForm(activityForm);

            //时间格式处理
            List<RsActivity> rsActivityList = activityMapper.getRsActivityList(rsActivity.getCompanyId());
            rsActivityList.forEach(data -> {

                String activityDate1 = data.getActivityStartTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                String week1 = DateUtil.week(data.getActivityStartTime().getDayOfWeek().getValue());
                data.setActivityTime(activityDate1 + " " + week1);
            });

            rsCompany.setActivityCount(activityCount);
            rsCompany.setFocusStatus(focusStatus);
            rsCompany.setFansCount(fansCount);
            rsCompany.setRsActivityList(rsActivityList);

            rsActivity.setCollectCount(collectCount);
            rsActivity.setRsCompany(rsCompany);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询活动详情异常");
        }
        return rsActivity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RsActivityManage> getCompanyActivityList(PagingTool pagingTool) {

        List<RsActivityManage> activityManageList;
        try {
            activityManageList = activityMapper.getCompanyActivityList(pagingTool);
            if (null != activityManageList && activityManageList.size() > 0) {
                //过滤出需要付费的活动id列表
                List<Integer> payList = activityManageList.stream().filter(RsActivityManage::getIsFree).
                        map(RsActivityManage::getActivityId).collect(Collectors.toList());

                if (payList.size() > 0) {

                    //查询出付费活动列表票信息
                    List<RsActivityManage> activityTicketFee = ticketMapper.getActivityTicketFee(payList);
                    //
                    if (null != activityTicketFee) {
                        activityTicketFee.forEach(data -> {
                            for (RsActivityManage activityManage : activityManageList) {
                                //票价赋值
                                if (data.getActivityId() == activityManage.getActivityId()) {

                                    activityManage.setTicketPrice(data.getTicketPrice());
                                }
                            }
                        });
                    }

                }
                //查询报名人数
                List<RsActivityManage> signUpCount = activityOrderMapper.getSignUpCount(activityManageList);
                if (null != signUpCount && signUpCount.size() > 0) {
                    signUpCount.forEach(data -> {
                        for (RsActivityManage activityManage : activityManageList) {
                            //票价赋值
                            if (data.getActivityId() == activityManage.getActivityId()) {

                                activityManage.setSignUpCount(data.getSignUpCount());
                            }
                        }
                    });
                }
                //对活动时间进行处理
                for (RsActivityManage activityManage : activityManageList) {

                    String activityWeek = DateUtil.ActivityWeek(activityManage.getActivityStartTime());
                    activityManage.setStartTime(activityWeek);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("查询公司名下活动列表异常");
        }

        return activityManageList;
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyActivityInfo getCompanyActivityInfo(String activityId) {

        CompanyActivityInfo companyActivityInfo;
        try {
            companyActivityInfo = activityMapper.getCompanyActivityInfo(activityId);
            if (null == companyActivityInfo) {
                throw new Exception("获取公司详细活动信息失败");
            }
            String activityWeek = DateUtil.ActivityWeek(companyActivityInfo.getActivityStartTime());

            //查询收藏活动数量
            int collectCount = collectMapper.selectCollectCount(activityId, 2);

            //查询活动浏览数
            PagingTool pagingTool = new PagingTool();
            Map<String, Object> map = new HashMap<>(12);
            map.put("relationId", activityId);
            map.put("pvType", 1);
            pagingTool.setParams(map);
            int pvCount = pvMapper.countTotal(pagingTool);
            companyActivityInfo.setPvCount(pvCount);

            companyActivityInfo.setActivityTime(activityWeek);
            companyActivityInfo.setCollectCount(collectCount);

            //查询活动票信息
            List<Ticket> tickets = ticketMapper.selectTicketList(activityId);

            //所有已售出活动票数量
            int saleTicketCount = activityOrderMapper.getSaleTicketCount(Integer.parseInt(activityId));

            //查询售出的免费票数量
            int freeCount = activityOrderMapper.getFreeCount(Integer.parseInt(activityId));

            //售出的付费票数量
            int payCount = saleTicketCount - freeCount;
            for (Ticket ticket : tickets) {
                int surplus;
                //免费票
                if (ticket.getTicketFee() == 0) {
                    surplus = ticket.getAmount() - freeCount;
                } else {
                    surplus = ticket.getAmount() - payCount;
                }

                if (surplus < 0) {
                    surplus = 0;
                }
                ticket.setAmount(surplus);
            }

            companyActivityInfo.setTicket(tickets);

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("获取公司详细活动信息异常");
        }

        return companyActivityInfo;
    }

    @Override
    public int countTotal(PagingTool pageTool) {
        int count;

        try {
            count = activityMapper.countTotal(pageTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取活动异常！");
        }
        return count;
    }

    @Override
    public int popularCountTotal(PagingTool pageTool) {
        int count;

        try {
            count = activityMapper.popularCountTotal(pageTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取活动异常！");
        }
        return count;
    }

    @Override
    public Activity queryActivityInfo(String activityId) {

        Activity activity;
        try {
            activity = activityMapper.selectByPrimaryKey(Integer.parseInt(activityId));

            Ticket ticket = ticketMapper.selectByActivityId(Integer.parseInt(activityId));

            activity.setTicket(ticket);

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("获取详细活动信息异常");
        }

        return activity;
    }

    @Override
    public int getCompanyActivityCount(PagingTool pagingTool) {
        int count;

        try {
            count = activityMapper.getCompanyActivityCount(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取公司名下活动总数异常！");
        }
        return count;
    }

    @Override
    public int batchUpdateActivity(List<Activity> activityList) {
        int count;

        try {

            count = activityMapper.batchUpdateActivity(activityList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("更新活动状态异常！");
        }
        return count;
    }

    @Override
    public List<RsCollect> getCollectActivityList(List<Integer> relationIdList, PagingTool pageTool) {
        List<RsCollect> collectActivityList;
        try {

            collectActivityList = activityMapper.getCollectActivityList(relationIdList, pageTool);
            for (RsCollect rsCollect : collectActivityList) {

                String activityWeek = DateUtil.ActivityWeek(rsCollect.getActivityStartTime());

                rsCollect.setActivityTime(activityWeek);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取收藏活动列表异常");
        }
        return collectActivityList;
    }
}
