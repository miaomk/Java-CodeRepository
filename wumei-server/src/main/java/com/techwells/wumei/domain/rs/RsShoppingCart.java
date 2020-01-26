package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.ShoppingCart;

public class RsShoppingCart extends ShoppingCart{
	
	private String productName;

    private String productIcon;
    
    private Double salePrice;
    
    private String productProfile;

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

	public String getProductProfile() {
		return productProfile;
	}

	public void setProductProfile(String productProfile) {
		this.productProfile = productProfile;
	}
    
    

}
