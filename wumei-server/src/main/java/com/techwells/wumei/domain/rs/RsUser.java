package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Merchant;
import com.techwells.wumei.domain.User;
import lombok.Data;

@Data
public class RsUser extends User{
	
	
    private Merchant merchant;
	/**
	 * 关注数量
	 */
    private int focusCount;
	/**
	 * 收藏数量
	 */
    private int collectCount;
	/**
	 * 优惠券数量
	 */
	private int receiveCount;



}
