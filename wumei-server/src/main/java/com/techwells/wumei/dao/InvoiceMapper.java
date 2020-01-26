package com.techwells.wumei.dao;

import java.util.List;

import com.techwells.wumei.domain.Invoice;
import com.techwells.wumei.util.PagingTool;
import org.apache.ibatis.annotations.Param;

public interface InvoiceMapper {
    int deleteByPrimaryKey(Integer invoiceId);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    Invoice selectByPrimaryKey(Integer invoiceId);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);
    
 // 获取列表
 	int countTotal(PagingTool pagingTool);

 	List<Invoice> selectInvoiceList(PagingTool pagingTool);
 	
 	int batchDelete(@Param("ids")String[] ids);
}