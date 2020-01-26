package com.techwells.wumei.domain.rs;

public class VoMerchantOrderProduct {
	
	 private String productName;

	    private String specification;

	    private String productIcon;

	    private Double salePrice;
	    
	    private Integer stock;
	    
	    private Integer saleCount;

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getSpecification() {
			return specification;
		}

		public void setSpecification(String specification) {
			this.specification = specification;
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

		public Integer getStock() {
			return stock;
		}

		public void setStock(Integer stock) {
			this.stock = stock;
		}

		public Integer getSaleCount() {
			return saleCount;
		}

		public void setSaleCount(Integer saleCount) {
			this.saleCount = saleCount;
		}
	    
	    
	    
	    
}
