package com.zhrenjie04.alex.core;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户类
 * 
 * @author 张人杰
 *
 */
@ApiModel("用户对象")
public class User extends AbstractGenericEntity {
	private static final long serialVersionUID = -3683386137654425948L;
	public User() {
	}
	@ApiModelProperty("用户id")
	private String userId;
	@ApiModelProperty("用户名")
	@Pattern(regexp = "^[0-9a-zA-Z]{1,30}$", message = "用户名只能为1至30位英文字母或数字")
	private String username;
	@ApiModelProperty("原密码")
	private String oldPassword;
	@ApiModelProperty("密码")
	@NotEmpty(message = "密码不能为空")
	private String password;
	@ApiModelProperty("密码加密盐值")
	private String salt;
	@ApiModelProperty("真实姓名")
	private String realname;
	@ApiModelProperty("昵称")
	@NotEmpty(message = "昵称不能为空")
	private String nickname;
	@ApiModelProperty("电子邮箱")
	@Email(message = "电子邮箱格式不正确")
	private String email;
	@ApiModelProperty("手机")
	@Pattern(regexp = "^(1(3|4|5|7|8)\\d{9})|()$", message = "手机号码格式不正确")
	private String cellphone;
	@ApiModelProperty("头像Url")
	private String portraitUrl;
	@ApiModelProperty("微信号")
	private String weixin;
	@ApiModelProperty("生日")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@ApiModelProperty("性别")
	private String gender;
	@ApiModelProperty("是否已锁定")
	private Boolean isLocked = false;
	@ApiModelProperty("最后登录IP")
	private String lastLoginIp;
	@ApiModelProperty("最后登录时间")
	@JsonSerialize(using=AlexTimestampSerializer.class)
	private Date lastLoginTime;
	@ApiModelProperty("jobs")
	private List<Job> jobs = new LinkedList<Job>();
	@ApiModelProperty("当前职务jobId")
	private String currentJobId;
	@ApiModelProperty("权限Codes")
	private List<String> privilegeCodes = new LinkedList<String>();
	@ApiModelProperty("角色ids，用于不能删除自己的角色的判断")
	private List<String> currentRoleIds = new LinkedList<String>();
	@ApiModelProperty("主功能菜单")
	private List<MenuItem> menuLinks = new LinkedList<MenuItem>();
	@ApiModelProperty("账号所属集团id")
	private String groupId;
	@ApiModelProperty("验证码传输字段")
	private String captcha;
	@ApiModelProperty("是否修改密码")
	private Boolean notChangePassword;
	@ApiModelProperty("扩展字段，用于向前端传递好友备注信息")
	private String friendMemo;
	@ApiModelProperty("扩展字段，用于显示BigDecimal的处理过程")
	private BigDecimal bigDecimalTag=new BigDecimal("2.9");

	private HashMap<String, Job> idToJobMap = new HashMap<String, Job>();

	public boolean hasPrivilege(String privilegeCode) {
		if (privilegeCodes == null) {
			return false;
		} else {
			return privilegeCodes.contains(privilegeCode);
		}
	}

	@JsonIgnore
	public Job getCurrentJob() {
		if (currentJobId == null || "".equals(currentJobId)) {
			return new Job();
		} else {
			return idToJobMap.get(currentJobId);
		}
	}

	public Boolean getNotChangePassword() {
		return notChangePassword;
	}

	public void setNotChangePassword(Boolean notChangePassword) {
		this.notChangePassword = notChangePassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public List<MenuItem> getMenuLinks() {
		return menuLinks;
	}

	public void setMenuLinks(List<MenuItem> menuLinks) {
		this.menuLinks = menuLinks;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getCurrentJobId() {
		return currentJobId;
	}

	public boolean setCurrentJobId(String currentJobId) {
		this.currentJobId = currentJobId;
		if(idToJobMap.get(currentJobId)!=null) {
			return true;
		}else {
			return false;
		}
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
		idToJobMap.clear();
		if(jobs!=null) {
			for (Job job : jobs) {
				idToJobMap.put(job.getJobId(), job);
			}
		}
	}

	@Override
	public String getPK() {
		return userId;
	}

	@Override
	public void setPK(String id) {
		this.userId = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<String> getPrivilegeCodes() {
		return privilegeCodes;
	}

	public void setPrivilegeCodes(List<String> privilegeCodes) {
		this.privilegeCodes = privilegeCodes;
	}

	public List<String> getCurrentRoleIds() {
		return currentRoleIds;
	}

	public void setCurrentRoleIds(List<String> currentRoleIds) {
		this.currentRoleIds = currentRoleIds;
	}

	public String getFriendMemo() {
		return friendMemo;
	}

	public void setFriendMemo(String friendMemo) {
		this.friendMemo = friendMemo;
	}

	public BigDecimal getBigDecimalTag() {
		return bigDecimalTag;
	}

	public void setBigDecimalTag(BigDecimal bigDecimalTag) {
		this.bigDecimalTag = bigDecimalTag;
	}
	
}