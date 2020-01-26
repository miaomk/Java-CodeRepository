package com.techwells.wumei.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Role implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer roleId;

    private String roleName;

    private String authoritys;

    private String description;

    private Boolean activated;

    private Boolean deleted;

    private Date updatedDate;

    private Date createdDate;
    
    private List<Authority> authorityList;
    

    public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(String authoritys) {
        this.authoritys = authoritys == null ? null : authoritys.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}