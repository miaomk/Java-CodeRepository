package com.techwells.wumei.domain;

/**
 * @author Administrator
 *
 */
public class ReceiveShopping {
	private int productId;
	private int productNum;
	private double salePrice;
	private Double activityPrice;
	private Double purchasePrice;
	private String specification;


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public int getProductNum() {
		return productNum;
	}


	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}


	public double getSalePrice() {
		return salePrice;
	}


	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(Double activityPrice) {
		this.activityPrice = activityPrice;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}


	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}


	public String getSpecification() {
		return specification;
	}


	public void setSpecification(String specification) {
		this.specification = specification;
	}


	public ReceiveShopping(int productId, int productNum, double salePrice, double purchasePrice, String specification) {
		super();

		this.productId = productId;
		this.productNum = productNum;
		this.salePrice = salePrice;
		this.purchasePrice = purchasePrice;
		this.specification = specification;
	}


	

	
	
}

	
