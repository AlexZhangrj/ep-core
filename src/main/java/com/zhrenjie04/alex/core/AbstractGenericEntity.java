package com.zhrenjie04.alex.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 实体类的抽象类，所有实体类需要集成此类 梳理架构，版本2.2
 * 
 * @author 张人杰
 *
 *         每个实体类都需要有一个主键id 注意：id采用雪花算法产生，数据库中用bigint，java中用String，前端用String
 */
@SuppressWarnings("serial")
public abstract class AbstractGenericEntity implements Serializable {
	/**
	 * 返回主键
	 * 
	 * @return 主键
	 */
	@JsonIgnore
	public abstract String getPK();

	/**
	 * 主键
	 * 
	 * @param id
	 */
	public abstract void setPK(String id);

	protected HashMap<String, Object> otherParams = new LinkedHashMap<String, Object>(16);
	protected HashMap<String, Object> otherResults = new LinkedHashMap<String, Object>(16);
	/**
	 * 删除标记
	 */
	private Boolean isDeleted = false;
	/**
	 * 创建时间
	 */
	@JsonSerialize(using=AlexTimestampSerializer.class)
	private Date createdTime;
	/**
	 * 创建者id
	 */
	private String creatorId;
	/**
	 * 创建者真实姓名
	 */
	private String creatorName;
	/**
	 * 最后修改时间
	 */
	@JsonSerialize(using=AlexTimestampSerializer.class)
	private Date lastModifiedTime;
	/**
	 * 最后修改者id
	 */
	private String lastModifierId;
	/**
	 * 最后修改者真实姓名
	 */
	private String lastModifierName;

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(String lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	public String getLastModifierName() {
		return lastModifierName;
	}

	public void setLastModifierName(String lastModifierName) {
		this.lastModifierName = lastModifierName;
	}

	public HashMap<String, Object> getOtherParams() {
		return otherParams;
	}

	public void setOtherParams(HashMap<String, Object> otherParams) {
		this.otherParams = otherParams;
	}

	public HashMap<String, Object> getOtherResults() {
		return otherResults;
	}

	public void setOtherResults(HashMap<String, Object> otherResults) {
		this.otherResults = otherResults;
	}
}