package com.zhrenjie04.alex.manager.domain;

import java.util.LinkedList;
import java.util.List;

import com.zhrenjie04.alex.core.AbstractGenericEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"privilegeId"}, callSuper = false)
@ToString
public class Privilege extends AbstractGenericEntity {

	private static final long serialVersionUID = -5526208368174025045L;

	private String privilegeId;
	private String privilegeName;
	private String privilegeCode;
	private String frontEndSystem;
	private String simpleUrl;
	private String requestMethod;
	private Boolean isMenu;
	private String parentId;
	private String idPath;
	private Boolean isLocked;
	private String iconClass;
	private String iconType;
	private String ab;
	private String collapse;
	private Double sortValue;
	private List<Privilege> children=new LinkedList<Privilege>();

	@Override
	public String getPK() {
		return this.privilegeId;
	}

	@Override
	public void setPK(String id) {
		this.privilegeId=id;
	}
}
