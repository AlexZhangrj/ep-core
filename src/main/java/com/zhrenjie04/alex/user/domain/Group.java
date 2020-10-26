package com.zhrenjie04.alex.user.domain;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"groupId"}, callSuper = false)
@ToString
public class Group {
	private String groupId;
	private String groupName;
	private String groupShortName;
	private String groupCode;
	private String parentId;
	private String idPath;
	private Boolean isLocked=false;
	private Boolean isDeleted=false;
	private Date createdTime;
	private String createrId;
	private String createrName;
	private Date lastModifiedTime;
	private String lastModifierId;
	private String lastModifierName;
}
