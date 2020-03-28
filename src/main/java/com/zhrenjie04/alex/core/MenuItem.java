package com.zhrenjie04.alex.core;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张人杰
 */
public class MenuItem implements Serializable {
	private String menuId;
	private String parentId;
	private String path;
	private String menuName;
    private String title;
    private String type;
    private String iconclass;
    private String icontype;
    private String collapse;
    private String menuCode;
    private String frontEndSystem;
    /**
     * 子菜单
     */
    private List<MenuItem>children;
    /**
     * 子菜单简写
     */
    private String ab;
    
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIconclass() {
		return iconclass;
	}
	public void setIconclass(String iconclass) {
		this.iconclass = iconclass;
	}
	public String getIcontype() {
		return icontype;
	}
	public void setIcontype(String icontype) {
		this.icontype = icontype;
	}
	public String getCollapse() {
		return collapse;
	}
	public void setCollapse(String collapse) {
		this.collapse = collapse;
	}
	public List<MenuItem> getChildren() {
		return children;
	}
	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}
	public String getAb() {
		return ab;
	}
	public void setAb(String ab) {
		this.ab = ab;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	@Override
	public int hashCode() {
		return menuId.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MenuItem) {
			if(((MenuItem)obj).getMenuId().equals(this.menuId)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	public String getFrontEndSystem() {
		return frontEndSystem;
	}
	public void setFrontEndSystem(String frontEndSystem) {
		this.frontEndSystem = frontEndSystem;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
}
