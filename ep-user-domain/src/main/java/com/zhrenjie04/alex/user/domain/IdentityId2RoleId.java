package com.zhrenjie04.alex.user.domain;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of= {"id"})
public class IdentityId2RoleId{
	private String id;
	private String identityId;
	private String roleId;
	private String groupId;
	private String userId;
	private Boolean isLocked;
	private Boolean isDeleted;
	private Date createdTime;
	private String createrId;
	private String createrName;
	private Date lastModifiedTime;
	private String lastModifierId;
	private String lastModifierName;
}
