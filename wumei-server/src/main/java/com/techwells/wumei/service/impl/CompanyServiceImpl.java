package com.techwells.wumei.service.impl;

import com.techwells.wumei.dao.*;
import com.techwells.wumei.domain.Admin;
import com.techwells.wumei.domain.Company;
import com.techwells.wumei.domain.User;
import com.techwells.wumei.domain.rs.RsCompany;
import com.techwells.wumei.service.CompanyService;
import com.techwells.wumei.util.MD5;
import com.techwells.wumei.util.PagingTool;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miao
 */
@Service("CompanyService")
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private FocusMapper focusMapper;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AdminMapper adminMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addCompany(RsCompany company) {
        int count;
        try {
            count = companyMapper.insertSelective(company);

            User user = new User();
            user.setUserId(company.getUserId());
            user.setUserType(2);
            user.setCompanyId(company.getCompanyId());
            user.setPosition(company.getPosition());
            user.setCompany(company.getCompanyName());

            userMapper.updateByPrimaryKeySelective(user);
            if (count < 0) {
                throw new Exception("主办方认证失败!");
            }
            //生成后台管理系统账号
            Admin admin = new Admin();
            admin.setAdminId(String.valueOf(company.getUserId()));
            //账号 公司联系方式
            admin.setAdminName(company.getContact());
            //密码 默认123456
            admin.setPassword(MD5.md5("123456"));
            admin.setMobile(company.getContact());
            admin.setDescription(company.getCompanyName() + "的管理员账号");
            //设置主办方角色权限
            admin.setRoleId(4);
            //启用账号
            admin.setActivated(1);
            adminMapper.insertSelective(admin);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("主办方认证异常！");
        }

        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public Company getCompanyById(Integer companyId) {
        Company company = new Company();
        try {
            company = companyMapper.selectByPrimaryKey(companyId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCompany(Integer companyId) {
        int count;
        try {

            count = companyMapper.deleteCompany(companyId);
            if (count < 0) {
                throw new Exception("删除公司失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除公司异常！");
        }

        return count;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeleteCompany(String[] companyIdArrays) {
        int count;

        try {

            count = companyMapper.batchDeleteCompany(companyIdArrays);
            if (count < 0) {
                throw new Exception("删除公司失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("删除公司异常！");
        }

        return count;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyCompany(Company company) {

        int count;

        try {

            count = companyMapper.updateByPrimaryKeySelective(company);
            if (count < 0) {
                throw new Exception("修改公司信息失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("修改公司信息异常！");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyCompanyStatus(String companyId, String activated) {
        int count;

        try {

            Company company = new Company();

            company.setCompanyId(Integer.parseInt(companyId));
            company.setActivated(Integer.parseInt(activated));

            count = companyMapper.updateByPrimaryKeySelective(company);
            if (count < 0) {
                throw new Exception("审核公司信息失败!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("审核公司信息异常！");
        }

        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchModifyCompanyStatus(String[] companyIds, String activated) {
        int count;

        try {

            count = companyMapper.batchModifyCompanyStatus(companyIds, Integer.parseInt(activated));
            if (count < 0) {
                throw new Exception("修改公司信息失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改公司信息异常！");
        }

        return count;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RsCompany> getCompanyList(PagingTool pageTool) {
        List<RsCompany> companyList;

        try {

            companyList = companyMapper.getCompanyList(pageTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取技术人员列表异常");
        }
        return companyList;
    }

    @Override
    @Transactional(readOnly = true)
    public RsCompany getCompanyInfoByUserId(Integer userId) {

        RsCompany rsCompany;
        try {
            rsCompany = companyMapper.getCompanyByUserId(userId);
            if (null == rsCompany) {
                return null;
            }
            int fansCount = focusMapper.focusCount(rsCompany.getCompanyId());
            int activityCount = activityMapper.getActivityCount(rsCompany.getCompanyId());
            rsCompany.setFansCount(fansCount);
            rsCompany.setActivityCount(activityCount);
        } catch (Exception e) {
            e.printStackTrace();

            throw new ApplicationContextException("获取用户主办方异常");
        }

        return rsCompany;
    }

    @Override
    public RsCompany getCompanyInfoByCompanyId(Integer companyId) {
        RsCompany rsCompany;
        try {
            rsCompany = companyMapper.getCompanyByCompanyId(companyId);
            int fansCount = focusMapper.focusCount(rsCompany.getCompanyId());
            int activityCount = activityMapper.getActivityCount(rsCompany.getCompanyId());
            rsCompany.setFansCount(fansCount);
            rsCompany.setActivityCount(activityCount);
        } catch (Exception e) {
            e.printStackTrace();

            throw new ApplicationContextException("获取公司信息异常");
        }

        return rsCompany;
    }


    @Override
    public Company getCompany(Integer companyId) {
        Company company;
        try {
            company = companyMapper.selectByPrimaryKey(companyId);
        } catch (Exception e) {
            e.printStackTrace();

            throw new ApplicationContextException("获取公司信息异常");
        }


        return company;
    }

    @Override
    public int countTotal(PagingTool pageTool) {
        int count;

        try {
            count = companyMapper.countTotal(pageTool);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationContextException("获取公司总数异常！");
        }
        return count;
    }

    @Override
    public Company getCompanyInfo(String userId) {

        Company company;
        try {
            company = companyMapper.getCompanyInfo(userId);

        } catch (Exception e) {
            e.printStackTrace();

            throw new ApplicationContextException("获取用户主办方信息异常");
        }

        return company;
    }
}
