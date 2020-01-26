package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Purchase;

public class RsPurchase extends Purchase{
	private String merchantName;
	
	private String productName;

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	

}
