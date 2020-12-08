package com.zhrenjie04.alex.core;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author 张人杰
 */
public class TreeNode<PK extends Serializable> extends JsonTreeNode<PK>{
	private HashMap<PK,TreeNode<PK>>childrenMap=new HashMap<PK,TreeNode<PK>>(0);
	private PK pid;
	/**
	 * id路径，生成树时的结构来源
	 */
	private String idPath;
	
	public HashMap<PK, TreeNode<PK>> getChildrenMap() {
		return childrenMap;
	}
	public void setChildrenMap(HashMap<PK, TreeNode<PK>> childrenMap) {
		this.childrenMap = childrenMap;
	}
	public String getIdPath(){
		return idPath;
	}
	public void setIdPath(String idPath){
		this.idPath=idPath;
	}
	public PK getPid(){
		return pid;
	}
	public void setPid(PK pid){
		this.pid=pid;
	}
}
