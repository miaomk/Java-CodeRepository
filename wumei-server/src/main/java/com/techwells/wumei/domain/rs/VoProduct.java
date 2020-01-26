package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.*;

import java.util.List;

public class VoProduct extends Product {
	
	
	private List<Groupon> grouponList;
	private List<ProductImage> imageList;
	private List<RsEvaluation> evaluationList;
	
	private List<Stock> stockList;
	
	private List<RsJoin> joinList;

	private List<SpecificationsProduct> specificationsList;

	public List<SpecificationsProduct> getSpecificationsList() {
		return specificationsList;
	}

	public void setSpecificationsList(List<SpecificationsProduct> specificationsList) {
		this.specificationsList = specificationsList;
	}

	private Brand brand;
	public List<Groupon> getGrouponList() {
		return grouponList;
	}
	public void setGrouponList(List<Groupon> grouponList) {
		this.grouponList = grouponList;
	}
	public List<ProductImage> getImageList() {
		return imageList;
	}
	public void setImageList(List<ProductImage> imageList) {
		this.imageList = imageList;
	}
	public List<RsEvaluation> getEvaluationList() {
		return evaluationList;
	}
	public void setEvaluationList(List<RsEvaluation> evaluationList) {
		this.evaluationList = evaluationList;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public List<Stock> getStockList() {
		return stockList;
	}
	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}
	public List<RsJoin> getJoinList() {
		return joinList;
	}
	public void setJoinList(List<RsJoin> joinList) {
		this.joinList = joinList;
	}
	
}
