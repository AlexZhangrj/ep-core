package com.zhrenjie04.alex.core;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 张人杰
 */
@Data
@EqualsAndHashCode(of = {"identityId"}, callSuper = false)
@ToString
public class Identity extends AbstractGenericEntity{
	
	private static final long serialVersionUID = 6547730311451225436L;

	public static final String JOB_TYPE_ORG_POSITION_JOB="orgPositionJob";
	public static final String JOB_TYPE_PROJECT_DEPT_POSITION_JOB="projectDeptPositionJob";
	private String identityId;
	private String userId;
	private String groupId;
	private String positionId;
	/** 只用于前端显示 */
	private String groupName;
	/** 只用于前端显示 */
	private String positionName;
	/** 该身份已锁定 */
	private Boolean isLocked;
	@Override
	public String getPK() {
		return identityId;
	}
	@Override
	public void setPK(String id) {
		this.identityId = id;
	}
}
