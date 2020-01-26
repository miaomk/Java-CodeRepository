package com.techwells.wumei.service;

import java.util.List;

import com.techwells.wumei.domain.Invoice;
import com.techwells.wumei.util.PagingTool;

public interface InvoiceService {

	// 增删改查
	public int addInvoice(Invoice invoice);

	public int delInvoice(int invoiceId);

	public int modifyInvoice(Invoice invoice);

	Invoice getInvoiceByInvoiceId(int invoiceId);

	// 获取列表
	int countTotal(PagingTool pagingTool);

	List<Invoice> getInvoiceList(PagingTool pagingTool);

	// 批量删除
	int deleteBatch(String[] idArr);
}
