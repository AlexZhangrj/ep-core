package com.zhrenjie04.alex.manager.domain;

import com.zhrenjie04.alex.core.AbstractGenericEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@ToString
public class IdentityRole extends AbstractGenericEntity {

	private static final long serialVersionUID = -89075677069171700L;
	/** 主键id */
	private String id;
	/** 身份id */
	private String identityId;
	/** 用户id（用于冗余） */
	private String userId;
	/** 角色id */
	private String roleId;
	@Override
	public String getPK() {
		return id;
	}
	@Override
	public void setPK(String id) {
		this.id = id;
	}
}
