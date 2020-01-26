package com.techwells.wumei.domain.rs;

import java.util.List;

import com.techwells.wumei.domain.ProductType;

public class VoProductType extends ProductType{
	
	
    private List<ProductType> secondProductTypeList;

	public List<ProductType> getSecondProductTypeList() {
		return secondProductTypeList;
	}

	public void setSecondProductTypeList(List<ProductType> secondProductTypeList) {
		this.secondProductTypeList = secondProductTypeList;
	}
    
    
    

}
