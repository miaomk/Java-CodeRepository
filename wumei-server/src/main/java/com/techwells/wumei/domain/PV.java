package com.techwells.wumei.domain;

import java.util.Date;

public class PV {
    private Integer pvId;

    private Integer userId;

    private Integer relationId;

    private Integer pvType;

    private Date createTime;

    public Integer getPvId() {
        return pvId;
    }

    public void setPvId(Integer pvId) {
        this.pvId = pvId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Integer getPvType() {
        return pvType;
    }

    public void setPvType(Integer pvType) {
        this.pvType = pvType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}