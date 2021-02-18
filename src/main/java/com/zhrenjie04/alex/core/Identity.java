package com.zhrenjie04.alex.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 张人杰
 */
@Schema(name="身份对象")
@Data
@EqualsAndHashCode(of = {"identityId"}, callSuper = false)
@ToString
public class Identity implements Serializable{
	
	private static final long serialVersionUID = 6547730311451225436L;

	private String identityId;
	private String userId;
	private String groupId;
	private String identityName;
	/** 该身份已锁定 */
	private Boolean isLocked;
	/** 该身份已删除 */
	private Boolean isDeleted;
	private Date createdTime;
	private String creatorId;
	private String creatorName;
	private Date lastModifiedTime;
	private String lastModifierId;
	private String lastModifierName;

	/** 只用于前端显示 */
	private String groupType;
	/** 只用于前端显示 */
	private String groupName;
	/** 只用于前端显示 */
	private String groupShortName;
}
