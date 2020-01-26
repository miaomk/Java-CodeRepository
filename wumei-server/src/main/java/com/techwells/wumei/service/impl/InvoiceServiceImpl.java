package com.techwells.wumei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.techwells.wumei.dao.InvoiceMapper;
import com.techwells.wumei.domain.Invoice;
import com.techwells.wumei.service.InvoiceService;
import com.techwells.wumei.util.PagingTool;


@Service("InvoiceService")
public class InvoiceServiceImpl implements InvoiceService {

	private InvoiceMapper invoiceMapper;

	public InvoiceMapper getInvoiceMapper() {
		return invoiceMapper;
	}

	@Autowired
	public void setInvoiceMapper(InvoiceMapper invoiceMapper) {
		this.invoiceMapper = invoiceMapper;
	}

	@Override
	public int addInvoice(Invoice invoice) {
		int count = 0;
		try {
			count = invoiceMapper.insertSelective(invoice);
			if (count < 0) {
				throw new Exception("添加模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("添加模板异常！");
		}
		return count;
	}

	@Override
	public int delInvoice(int invoiceId) {
		int count = 0;
		try {
			count = invoiceMapper.deleteByPrimaryKey(invoiceId);
			if (count < 0) {
				throw new Exception("删除模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("删除模板异常！");
		}
		return count;
	}

	@Override
	public int modifyInvoice(Invoice invoice) {
		int count = 0;
		try {
			count = invoiceMapper.updateByPrimaryKeySelective(invoice);
			if (count < 0) {
				throw new Exception("修改模板失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("修改模板异常！");
		}
		return count;
	}

	@Override
	public Invoice getInvoiceByInvoiceId(int invoiceId) {
		Invoice invoice = null;
		try {
			invoice = invoiceMapper.selectByPrimaryKey(invoiceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板详情异常！");
		}
		return invoice;
	}

	// 获取列表
	@Override
	public int countTotal(PagingTool pagingTool) {
		int count = 0;

		try {
			count = invoiceMapper.countTotal(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板总数异常！");
		}
		return count;
	}

	@Override
	public List<Invoice> getInvoiceList(PagingTool pagingTool) {
		List<Invoice> invoiceList = null;

		try {
			invoiceList = invoiceMapper.selectInvoiceList(pagingTool);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationContextException("获取模板列表异常");
		}
		return invoiceList;
	}

	@Override
	public int deleteBatch(String[] idArr) {
		// TODO Auto-generated method stub
		return 0;
	}

}
