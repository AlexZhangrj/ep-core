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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 用户类
 * 
 * @author 张人杰
 *
 */
@ApiModel("用户对象")
@Data
@EqualsAndHashCode(of = {"userId"}, callSuper = false)
@ToString
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
	private List<Identity> identities = new LinkedList<Identity>();
	@ApiModelProperty("当前身份id")
	private String currentIdentityId;
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
	@ApiModelProperty("客户端类型")
	private String clientType;

	private HashMap<String, Identity> idToIdentityMap = new HashMap<String, Identity>();

	public boolean hasPrivilege(String privilegeCode) {
		if (privilegeCodes == null) {
			return false;
		} else {
			return privilegeCodes.contains(privilegeCode);
		}
	}

	@JsonIgnore
	public Identity getCurrentIdentity() {
		if (currentIdentityId == null || "".equals(currentIdentityId)) {
			return new Identity();
		} else {
			return idToIdentityMap.get(currentIdentityId);
		}
	}

	public String getCurrentIdentityId() {
		return currentIdentityId;
	}

	public boolean setCurrentIdentityId(String currentIdentityId) {
		if(idToIdentityMap.get(currentIdentityId)!=null) {
			this.currentIdentityId = currentIdentityId;
			return true;
		}else {
			return false;
		}
	}

	public void setIdentities(List<Identity> identities) {
		this.identities = identities;
		idToIdentityMap.clear();
		if(identities!=null) {
			for (Identity identity : identities) {
				idToIdentityMap.put(identity.getIdentityId(), identity);
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
	
}