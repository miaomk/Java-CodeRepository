package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.CompanyMapper;
import com.techwells.wumei.dao.FocusMapper;
import com.techwells.wumei.domain.Activity;
import com.techwells.wumei.domain.Focus;
import com.techwells.wumei.domain.rs.RsActivity;
import com.techwells.wumei.domain.rs.RsFocus;
import com.techwells.wumei.service.FocusService;
import com.techwells.wumei.util.PagingTool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户关注实现类
 *
 * @author miao
 */
@Service("FocusService")
public class FocusServiceImpl implements FocusService {

    @Resource
    private FocusMapper focusMapper;

    @Resource
    private CompanyMapper companyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addFocus(Focus focus) {
        int count;
        try {

            count = focusMapper.insertSelective(focus);
            if (count < 0) {
                throw new Exception("关注失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("关注异常！");
        }

        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RsFocus> getFocusCompanyList(Integer userId) {
        List<RsFocus> rsFocusList;
        try {

            rsFocusList = focusMapper.selectFocusListByUserId(userId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询关注异常");
        }

        return rsFocusList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteFocus(Integer focusId) {
        int count;
        try {

            count = focusMapper.deleteByPrimaryKey(focusId);
            if (count < 0) {
                throw new Exception("删除关注失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除关注异常！");
        }

        return count;
    }

    @Override
    public int queryFocusStatus(Integer userId, Integer relationId, Integer focusType) {
        int count;
        try {

            count = focusMapper.queryFocus(userId, relationId, focusType);
            if (count < 0) {
                throw new Exception("查寻关注状态失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查寻关注状态异常！");
        }

        return count;
    }

    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = focusMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取关注总数异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelFocus(Integer userId, Integer relationId, Integer focusType) {
        int count;
        try {

            count = focusMapper.cancelFocus(userId, relationId, focusType);
            if (count < 0) {
                throw new Exception("取消关注失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("取消关注异常！");
        }

        return count;
    }

    @Override
    public Integer getFocusId(Integer userId, Integer relationId, Integer focusType) {
        Integer focusId;
        try {

            focusId = focusMapper.getFocusId(relationId, String.valueOf(userId), focusType);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询关注异常！");
        }

        return focusId;
    }
}
