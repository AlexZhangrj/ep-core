package com.zhrenjie04.alex.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 张人杰
 */
public class JsonTreeNode<PK extends Serializable> {
	
	private PK id;
	private String text;
	private String state;
	private boolean checked=false;
	private String iconCls;
	private HashMap<String,Object>attributes=new HashMap<String,Object>(0);
	private List<JsonTreeNode<PK>>children=new LinkedList<JsonTreeNode<PK>>();
	
	public List<JsonTreeNode<PK>> getChildren() {
		return children;
	}
	public void setChildren(List<JsonTreeNode<PK>> children) {
		this.children = children;
	}
	public PK getId() {
		return id;
	}
	public void setId(PK id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public HashMap<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}
}
