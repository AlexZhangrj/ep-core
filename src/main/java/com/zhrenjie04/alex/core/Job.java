package com.zhrenjie04.alex.core;

import java.io.Serializable;

/**
 * @author 张人杰
 */
public class Job implements Serializable{
	public static final String JOB_TYPE_ORG_POSITION_JOB="orgPositionJob";
	public static final String JOB_TYPE_PROJECT_DEPT_POSITION_JOB="projectDeptPositionJob";
	private String jobId;
	private String userId;
	private String jobName;
	private String jobType;
	private String orgId;
	private String projectDeptId;
	private String comId;
	private String groupId;
	private String positionId;
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getProjectDeptId() {
		return projectDeptId;
	}
	public void setProjectDeptId(String projectDeptId) {
		this.projectDeptId = projectDeptId;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Job) {
			if(this.getJobId()!=null) {
				if(this.getJobId().equals(((Job)obj).getJobId())) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public int hashCode() {
		if(this.getJobId()==null) {
			return 0;
		}else {
			return this.getJobId().hashCode();
		}
	}
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
}
