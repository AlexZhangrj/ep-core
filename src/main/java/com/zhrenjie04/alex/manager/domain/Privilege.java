package com.zhrenjie04.alex.manager.domain;

import java.util.LinkedList;
import java.util.List;

import com.zhrenjie04.alex.core.AbstractGenericEntity;

/**
 * Model class of t_privilege.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class Privilege extends AbstractGenericEntity {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** privilege_id. */
	private String privilegeId;

	/** privilege_name. */
	private String privilegeName;

	/** privilege_code. */
	private String privilegeCode;

	/** front_end_system. */
	private String frontEndSystem;

	/** simple_url. */
	private String simpleUrl;

	/** request_method. */
	private String requestMethod;

	/** is_menu. */
	private Boolean isMenu;

	/** parent_id. */
	private String parentId;

	/** id_path. */
	private String idPath;

	/** is_locked. */
	private Boolean isLocked;
	
	/** icon_class. */
	private String iconClass;
	
	/** icon_type. */
	private String iconType;

	/** ab. */
	private String ab;

	/** collapse. */
	private String collapse;

	/** sort_value. */
	private Double sortValue;

	private List<Privilege> children=new LinkedList<Privilege>();

	public String getPrivilegeId() {
		return privilegeId;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getIconType() {
		return iconType;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

	public String getAb() {
		return ab;
	}

	public void setAb(String ab) {
		this.ab = ab;
	}

	public String getCollapse() {
		return collapse;
	}

	public void setCollapse(String collapse) {
		this.collapse = collapse;
	}

	public Double getSortValue() {
		return sortValue;
	}

	public void setSortValue(Double sortValue) {
		this.sortValue = sortValue;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getSimpleUrl() {
		return simpleUrl;
	}

	public void setSimpleUrl(String simpleUrl) {
		this.simpleUrl = simpleUrl;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Boolean getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Boolean isMenu) {
		this.isMenu = isMenu;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((privilegeId == null) ? 0 : privilegeId.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Privilege other = (Privilege) obj;
		if (privilegeId == null) {
			if (other.privilegeId != null) {
				return false;
			}
		} else if (!privilegeId.equals(other.privilegeId)) {
			return false;
		}
		return true;
	}

	@Override
	public String getPK() {
		return this.privilegeId;
	}

	@Override
	public void setPK(String id) {
		this.privilegeId=id;
	}

	public List<Privilege> getChildren() {
		return children;
	}

	public void setChildren(List<Privilege> children) {
		this.children = children;
	}

	public String getFrontEndSystem() {
		return frontEndSystem;
	}

	public void setFrontEndSystem(String frontEndSystem) {
		this.frontEndSystem = frontEndSystem;
	}

}
