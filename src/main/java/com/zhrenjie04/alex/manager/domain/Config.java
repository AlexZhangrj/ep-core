package com.zhrenjie04.alex.manager.domain;

import java.util.LinkedList;
import java.util.List;

public class Config {
	private List<String> userIds = new LinkedList<String>();
	private String orgId;
	private String positionId;
	private List<String> roleIds = new LinkedList<String>();

	public List<String> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
}
