package com.techwells.wumei.service;

import com.techwells.wumei.domain.Company;
import com.techwells.wumei.domain.rs.RsCompany;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * @author miao
 */
public interface CompanyService {
    /**
     * 主办方认证
     *
     * @param company 公司信息
     * @return int
     */
    int addCompany(RsCompany company);

    /**
     * 根据公司id查询公司信息
     *
     * @param companyId
     * @return
     */
    Company getCompanyById(Integer companyId);

    /**
     * 删除公司
     *
     * @param companyId 公司id
     * @return int
     */
    int deleteCompany(Integer companyId);

    /**
     * 批量删除公司
     *
     * @param companyIdArrays 公司id数组
     * @return int
     */
    int batchDeleteCompany(String[] companyIdArrays);

    /**
     * 修改公司信息
     *
     * @param company 公司信息
     * @return int
     */
    int modifyCompany(Company company);

    /**
     * 审核公司状态
     *
     * @param companyId 公司id
     * @param activated 状态
     * @return int
     */
    int modifyCompanyStatus(String companyId, String activated);

    /**
     * 批量审核公司状态
     *
     * @param companyIds 公司id数组
     * @param activated  状态
     * @return int
     */
    int batchModifyCompanyStatus(String[] companyIds, String activated);

    /**
     * 查询公司列表
     *
     * @param pageTool 参数
     * @return List
     */
    List<RsCompany> getCompanyList(PagingTool pageTool);

    /**
     * 通过用户id获取公司详细信息
     *
     * @param userId 用户id
     * @return RsCompany
     */
    RsCompany getCompanyInfoByUserId(Integer userId);

    /**
     * 通过公司id获取公司详细信息
     *
     * @param companyId 公司id
     * @return RsCompany
     */
    RsCompany getCompanyInfoByCompanyId(Integer companyId);


    /**
     * 通过公司id查询公司信息
     *
     * @param companyId 公司id
     * @return Company
     */
    Company getCompany(Integer companyId);

    /**
     * 分页查询公司总数
     *
     * @param pageTool 分页
     * @return int
     */
    int countTotal(PagingTool pageTool);

    /**
     * 通过userID获取公司信息
     *
     * @param userId 用户id
     * @return Company
     */
    Company getCompanyInfo(String userId);
}
