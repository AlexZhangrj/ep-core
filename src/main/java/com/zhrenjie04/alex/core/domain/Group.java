package com.zhrenjie04.alex.core.domain;

import com.zhrenjie04.alex.core.enums.GroupTypeEnum;
import com.zhrenjie04.alex.core.enums.OrganizationCertificationTypeEnum;
import com.zhrenjie04.alex.core.enums.OrganizationTypeEnum;
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
	private OrganizationTypeEnum organizationType;
	private OrganizationCertificationTypeEnum organizationCertificationType;
	private String groupName;
	private String groupShortName;
	private String groupCode;
	private String parentId;
	private Boolean isLocked;
	private Boolean isDeleted;
	private Date createdTime;
	private String creatorId;
	private String creatorName;
	private Date lastModifiedTime;
	private String lastModifierId;
	private String lastModifierName;
}
