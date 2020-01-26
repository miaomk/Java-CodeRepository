package com.techwells.wumei.domain.rs;

import com.techwells.wumei.domain.Appeal;

/**
 * @author miaomaokao
 */
public class RsAppeal extends Appeal {

    private String nickName;

    private String actualAmount;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }
}
