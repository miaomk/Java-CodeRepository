package com.techwells.wumei.domain.rs;

import java.util.List;

import com.techwells.wumei.domain.ProductType;

public class BoProductType extends ProductType{
	
	private List<ProductType> children;

	public List<ProductType> getChildren() {
		return children;
	}

	public void setChildren(List<ProductType> children) {
		this.children = children;
	}

	
}
