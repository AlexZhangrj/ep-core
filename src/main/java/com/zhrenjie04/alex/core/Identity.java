package com.zhrenjie04.alex.core;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 张人杰
 */
@Data
@EqualsAndHashCode(of = {"identityId"}, callSuper = false)
@ToString
public class Identity implements Serializable{
	
	private static final long serialVersionUID = 6547730311451225436L;

	private String identityId;
	private String userId;
	private String groupId;
	private String positionId;
	/** 只用于前端显示 */
	private String groupName;
	/** 只用于前端显示 */
	private String groupShortName;
	/** 只用于前端显示 */
	private String positionName;
	/** 该身份已锁定 */
	private Boolean isLocked;
	/** 该身份已删除 */
	private Boolean isDeleted;

	private Date createdTime;
	private String createrId;
	private String createrName;
	private Date lastModifiedTime;
	private String lastModifierId;
	private String lastModifierName;

}
