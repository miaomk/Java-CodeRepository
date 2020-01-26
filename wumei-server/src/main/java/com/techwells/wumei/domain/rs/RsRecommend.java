
package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Recommend;

public class RsRecommend extends Recommend{

	private String productName;

    private String productIcon;

    private Double salePrice;

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

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
    
    
    
	
}
