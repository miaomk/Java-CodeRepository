package com.techwells.wumei.dao;

import com.techwells.wumei.domain.Company;
import com.techwells.wumei.domain.rs.RsActivity;
import com.techwells.wumei.domain.rs.RsCompany;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyMapper {
    int deleteByPrimaryKey(Integer companyId);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer companyId);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    /**
     * 查询公司名下的活动
     *
     * @param companyId 公司id
     * @return List
     */
    List<RsActivity> getCompanyActivity(Integer companyId);

    /**
     * 逻辑删除公司
     *
     * @param companyId 公司id
     * @return int
     */
    int deleteCompany(Integer companyId);

    /**
     * 批量逻辑删除公司
     *
     * @param companyIdArrays 公司id数组
     * @return int
     */
    int batchDeleteCompany(@Param("ids") String[] companyIdArrays);


    /**
     * 批量审核公司
     *
     * @param companyIdArrays 公司id数组
     * @param status          审核装态
     * @return int
     */
    int batchModifyCompanyStatus(@Param("ids") String[] companyIdArrays, @Param("status") Integer status);

    /**
     * 查询公司列表
     *
     * @param pageTool 参数
     * @return List
     */
    List<RsCompany> getCompanyList(PagingTool pageTool);

    /**
     * 通过用户id获取公司信息
     *
     * @param userId 用户id
     * @return RsCompany
     */
    RsCompany getCompanyByUserId(@Param("userId") Integer userId);

    /**
     * 通过公司id获取公司信息
     *
     * @param companyId 公司id
     * @return RsCompany
     */
    RsCompany getCompanyByCompanyId(@Param("companyId") Integer companyId);
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
    Company getCompanyInfo(@Param("userId") String userId);
}