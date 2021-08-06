package com.zhrenjie04.alex.core.domain;

import com.zhrenjie04.alex.core.enums.GroupTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(of = {"groupId"}, callSuper = false)
@ToString
public class Group {
	private String groupId;
	private GroupTypeEnum groupType;
	private String groupName;
	private String parentId;
	private Integer status;
	private String ownerUserId;
	private Boolean needApproval;
	private Boolean allowInviting;
	private Date createdTime;
}
