package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.AuthenticationMapper;
import com.techwells.wumei.dao.CompanyMapper;
import com.techwells.wumei.domain.Authentication;
import com.techwells.wumei.domain.Company;
import com.techwells.wumei.service.AuthenticationService;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 实名认证接口实现类
 *
 * @author miao
 */
@Service("AuthenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private AuthenticationMapper authenticationMapper;
    @Resource
    private CompanyMapper companyMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addAuthentication(Authentication authentication) {
        int count;
        try {
            count = authenticationMapper.insertSelective(authentication);
            if (count < 0) {
                throw new Exception("添加实名认证失败!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("添加实名认证异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delAuthentication(int authenticationId) {
        int count;
        try {
            count = authenticationMapper.deleteByPrimaryKey(authenticationId);
            if (count < 0) {
                throw new Exception("删除实名认证失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除实名认证异常！");
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyAuthentication(Authentication authentication) {
        int count;
        try {
            count = authenticationMapper.updateByPrimaryKeySelective(authentication);
            if (count < 0) {
                throw new Exception("修改实名认证失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改实名认证异常！");
        }
        return count;
    }

    @Override
    public Authentication getAuthenticationByAuthenticationId(int authenticationId) {
        Authentication authentication;
        try {
            authentication = authenticationMapper.selectByPrimaryKey(authenticationId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取实名认证详情异常！");
        }
        return authentication;
    }

    /**
     * 分页获取实名列表
     *
     * @param pagingTool 分页
     * @return int
     */
    @Override
    public int countTotal(PagingTool pagingTool) {
        int count;

        try {
            count = authenticationMapper.countTotal(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取实名认证总数异常！");
        }
        return count;
    }

    @Override
    public List<Authentication> getAuthenticationList(PagingTool pagingTool) {
        List<Authentication> authenticationList;

        try {
            authenticationList = authenticationMapper.selectAuthenticationList(pagingTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取实名认证列表异常");
        }
        return authenticationList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String[] idArr) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Authentication selectByIdCard(String idCard) {
        Authentication authentication;
        try {
            authentication = authenticationMapper.selectByIdCard(idCard);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取实名认证详情异常！");
        }
        return authentication;
    }

}
