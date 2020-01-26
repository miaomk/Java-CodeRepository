package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Evaluation;

public class RsEvaluation extends Evaluation{
	
	private String nickName;
	
	private String userIcon;
	
	private Integer productId;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	

}
