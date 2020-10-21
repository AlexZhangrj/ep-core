package com.zhrenjie04.alex.manager.domain;

import com.zhrenjie04.alex.core.AbstractGenericEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"groupId"}, callSuper = false)
@ToString
public class Group extends AbstractGenericEntity {
	
	private static final long serialVersionUID = 4029106866797502768L;

	private String groupId;
	/** 群组名称 */
	private String groupName;
	/** 群组介绍 */
	private String groupIntroduction;
	/** 群组公告 */
	private String groupNotice;
	/** 父级群组id */
	private String parentGroupId;
	@Override
	public String getPK() {
		return groupId;
	}
	@Override
	public void setPK(String id) {
		this.groupId = id;
	}
}
