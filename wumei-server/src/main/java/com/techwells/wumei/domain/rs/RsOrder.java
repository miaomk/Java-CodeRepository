package com.techwells.wumei.domain.rs;

import java.util.List;

import com.techwells.wumei.domain.Order;

public class RsOrder  extends Order{
	
    private String addressee;

    private String addresseePhone;
    
    private String address;
	
	private List<RsOrderProduct> orderProductList;

	public List<RsOrderProduct> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<RsOrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getAddresseePhone() {
		return addresseePhone;
	}

	public void setAddresseePhone(String addresseePhone) {
		this.addresseePhone = addresseePhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
