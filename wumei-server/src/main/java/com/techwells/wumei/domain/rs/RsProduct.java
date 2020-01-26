package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Product;

public class RsProduct extends Product{
	
	private String specification;	
	private Integer number;

	private String brandName;

	@Override
	public String getSpecification() {
		return specification;
	}

	@Override
	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
