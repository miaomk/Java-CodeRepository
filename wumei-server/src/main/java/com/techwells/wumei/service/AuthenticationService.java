package com.techwells.wumei.service;

import com.techwells.wumei.domain.Authentication;
import com.techwells.wumei.util.PagingTool;

import java.util.List;

/**
 * 实名认证接口
 *
 * @author miao
 */
public interface AuthenticationService {

    /**
     * 实名认证
     *
     * @param authentication 实名认证信息
     * @return int
     */
    int addAuthentication(Authentication authentication);

    /**
     * 通过实名认证id删除实名认证详情
     *
     * @param authenticationId 实名认证id
     * @return int
     */
    int delAuthentication(int authenticationId);

    /**
     * 修改实名认证信息
     *
     * @param authentication 实名认证信息
     * @return int
     */
    int modifyAuthentication(Authentication authentication);

    /**
     * 通过实名认证id获取实名认证详情
     *
     * @param authenticationId 实名认证id
     * @return Authentication
     */
    Authentication getAuthenticationByAuthenticationId(int authenticationId);

    /**
     * 分页获取实名总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 分页获取实名列表
     *
     * @param pagingTool 分页
     * @return int
     */
    List<Authentication> getAuthenticationList(PagingTool pagingTool);

    /**
     * 批量删除
     *
     * @param idArr id数组
     * @return int
     */
    int deleteBatch(String[] idArr);

    /**
     * 通过身份证查询实名信息是否已经存在
     *
     * @param idCard 身份证信息
     * @return Authentication
     */
    Authentication selectByIdCard(String idCard);
}
