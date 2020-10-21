package com.zhrenjie04.alex.manager.domain;

import com.zhrenjie04.alex.core.AbstractGenericEntity;

/**
 * Model class of t_job_user_project_dept_position.
 * 
 * @author generated by ERMaster
 * @version $Id$
 */
public class JobUserProjectDeptPosition extends AbstractGenericEntity {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** id. */
	private String jobId;

	/** user_id. */
	private String userId;

	/** project_dept_id. */
	private String projectDeptId;

	/** position_id. */
	private String positionId;
	
	private String groupId;
	
	/** is_locked. */
	private Boolean isLocked=false;
	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProjectDeptId() {
		return projectDeptId;
	}

	public void setProjectDeptId(String projectDeptId) {
		this.projectDeptId = projectDeptId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jobId == null) ? 0 : jobId.hashCode());
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
		JobUserProjectDeptPosition other = (JobUserProjectDeptPosition) obj;
		if (jobId == null) {
			if (other.jobId != null) {
				return false;
			}
		} else if (!jobId.equals(other.jobId)) {
			return false;
		}
		return true;
	}

	@Override
	public String getPK() {
		return this.jobId;
	}

	@Override
	public void setPK(String jobId) {
		this.jobId=jobId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

}
