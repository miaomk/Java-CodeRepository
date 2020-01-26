package com.techwells.wumei.dao;

import com.techwells.wumei.domain.Authentication;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthenticationMapper {
    int deleteByPrimaryKey(Integer authenticationId);

    int insert(Authentication record);

    int insertSelective(Authentication record);

    Authentication selectByPrimaryKey(Integer authenticationId);

    int updateByPrimaryKeySelective(Authentication record);

    /**
     * 根据实名认证id更新实名认证信息
     *
     * @param record 实名认证信息
     * @return int
     */
    int updateByPrimaryKey(Authentication record);

    /**
     * 分页获取实名列表
     *
     * @param pagingTool 分页
     * @return List
     */
    List<Authentication> selectAuthenticationList(PagingTool pagingTool);

    /**
     * 分页获取实名总数
     *
     * @param pagingTool 分页
     * @return int
     */
    int countTotal(PagingTool pagingTool);

    /**
     * 通过身份证查询实名信息是否已经存在
     *
     * @param idCard 身份证信息
     * @return Authentication
     */
    Authentication selectByIdCard(@Param("idCard") String idCard);
}