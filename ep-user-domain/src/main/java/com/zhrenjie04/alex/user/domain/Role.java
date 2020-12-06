package com.zhrenjie04.alex.user.domain;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"roleId"}, callSuper = false)
@ToString
public class Role {
	private String roleId;
	private String roleName;
	private String roleCode;
	private Boolean isLocked;
	private Boolean isDeleted;
	private Date createdTime;
	private String createrId;
	private String createrName;
	private Date lastModifiedTime;
	private String lastModifierId;
	private String lastModifierName;
}
