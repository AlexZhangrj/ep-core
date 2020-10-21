package com.zhrenjie04.alex.manager.domain;

import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.zhrenjie04.alex.core.AbstractGenericEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"roleId"}, callSuper = false)
@ToString
public class Role extends AbstractGenericEntity {

	private static final long serialVersionUID = 4552598221143797876L;

	private String roleId;
	@NotEmpty(message = "角色名称不能为空")
	private String roleName;
	private String roleCode;
	private Boolean isLocked;
	private String parentId;
	private String idPath;
	private List<Role> children=new LinkedList<Role>();

	@Override
	public String getPK() {
		return roleId;
	}

	@Override
	public void setPK(String id) {
		this.roleId=id;
	}

}
