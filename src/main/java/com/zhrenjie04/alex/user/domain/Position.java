package com.zhrenjie04.alex.user.domain;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"positionId"}, callSuper = false)
@ToString
public class Position {

	private String positionId;
	private String positionName;
	private String positionCode;
	private String groupId;
	private Boolean isLocked=false;
	private Boolean isDeleted=false;
	private Date createdTime;
	private String createrId;
	private String createrName;
	private Date lastModifiedTime;
	private String lastModifierId;
	private String lastModifierName;

}
