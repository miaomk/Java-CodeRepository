package com.techwells.wumei.domain.rs;


import com.techwells.wumei.domain.Score;

public class RsScore extends Score{
	
	
	 private String strategyName;

	 private Integer balanceType;

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public Integer getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(Integer balanceType) {
		this.balanceType = balanceType;
	}
    	
	

}
