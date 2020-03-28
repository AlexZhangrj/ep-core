package com.zhrenjie04.alex.core;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
/**
 * @author Alex
 * 关键参数：text、nodes
 * 可用参数：id、tags、href
 */
public class TreeViewNode<PK extends Serializable> extends LinkedHashMap<String,Object> {

	private static final long serialVersionUID = -182790159557363800L;

	public TreeViewNode(){
		put("id",0);
		put("text","");
		TreeViewNodeState state = new TreeViewNodeState();
		put("state",state);
		LinkedList<String> tags=new LinkedList<String>();
		put("tags",tags);
		put("parentId",0);
		LinkedList<TreeViewNode<PK>>nodes=new LinkedList<TreeViewNode<PK>>();
		put("nodes",nodes);
	}
	/**
	 * treeview关键数据
	 */
	public void setText(String text){
		put("text",text);
	}
	/**
	 * treeview关键数据
	 */
	public String getText(){
		return (String)get("text");
	}
	/**
	 * treeview关键数据
	 */
	public void setNodes(LinkedList<TreeViewNode<PK>> nodes){
		put("nodes",nodes);
	}
	/**
	 * treeview关键数据
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<TreeViewNode<PK>> getNodes(){
		return (LinkedList<TreeViewNode<PK>>)get("nodes");
	}
	/**
	 * treeview关键方法，移除小空子节点列表
	 */
	public void removeNodes(){
		remove("nodes");
	}
	/**
	 * 是否勾选
	 */
	public void setChecked(Boolean checked){
		((TreeViewNodeState)get("state")).setChecked(checked);;
	}
	/**
	 * 是否勾选
	 */
	public Boolean getChecked(){
		return ((TreeViewNodeState)get("state")).getChecked();
	}
	/**
	 * 是否选中
	 */
	public void setSelected(Boolean selected){
		((TreeViewNodeState)get("state")).setSelected(selected);;
	}
	/**
	 * 是否选中
	 */
	public Boolean getSelected(){
		return ((TreeViewNodeState)get("state")).getSelected();
	}
	/**
	 * 是否不能操作
	 */
	public void setDisabled(Boolean disabled){
		((TreeViewNodeState)get("state")).setDisabled(disabled);;
	}
	/**
	 * 是否不能操作
	 */
	public Boolean getDisabled(){
		return ((TreeViewNodeState)get("state")).getDisabled();
	}
	/**
	 * 是否展开
	 */
	public void setExpanded(Boolean expanded){
		((TreeViewNodeState)get("state")).setExpanded(expanded);
	}
	/**
	 * 是否展开
	 */
	public Boolean getExpanded(){
		return ((TreeViewNodeState)get("state")).getExpanded();
	}
	/**
	 * treeview关键数据
	 */
	public void setId(PK id){
		put("id",id);
	}
	/**
	 * treeview关键数据
	 */
	@SuppressWarnings("unchecked")
	public PK getId(){
		return (PK)get("id");
	}
	/**
	 * treeview关键数据
	 */
	public void setParentId(PK id){
		put("parentId",id);
	}
	/**
	 * treeview关键数据
	 */
	@SuppressWarnings("unchecked")
	public PK getParentId(){
		return (PK)get("parentId");
	}
	/**
	 * treeview可用数据
	 */
	public void setTags(LinkedList<String>tags){
		put("tags",tags);
	}
	/**
	 * treeview可用数据
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<String> getTags(){
		return (LinkedList<String>)get("tags");
	}
	/**
	 * treeview可用数据
	 */
	public void setHref(String href){
		put("href",href);
	}
	/**
	 * treeview可用数据
	 */
	public String getHref(){
		return (String)get("href");
	}
	/**
	 * treeview可用数据
	 */
	public void setState(TreeViewNodeState state){
		put("state",state);
	}
	/**
	 * treeview可用数据
	 */
	public TreeViewNodeState getState(){
		return (TreeViewNodeState)get("state");
	}
}
