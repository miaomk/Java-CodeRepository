package com.techwells.wumei.domain.rs;


import com.techwells.wumei.domain.OrderProduct;

public class RsOrderProduct extends OrderProduct {
	
	 private String productName;

	 private String productIcon;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductIcon() {
		return productIcon;
	}

	public void setProductIcon(String productIcon) {
		this.productIcon = productIcon;
	}	

}
