package com.zhrenjie04.alex.manager.domain;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.zhrenjie04.alex.core.AbstractGenericEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"positionId"}, callSuper = false)
@ToString
public class Position extends AbstractGenericEntity {

	private static final long serialVersionUID = -6387739039897728193L;

	private String positionId;
	@NotEmpty(message = "职位名称不能为空")
	private String positionName;
	@NotEmpty(message = "职位代码不能为空")
	private String positionCode;
	/** 该职位属于哪个群组 */
	private String groupId;
	private Boolean isLocked;

	@Override
	public String getPK() {
		return positionId;
	}

	@Override
	public void setPK(String id) {
		this.positionId = id;
	}
}
