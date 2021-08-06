package com.zhrenjie04.alex.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhrenjie04.alex.core.BasicEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户类
 * 
 * @author 张人杰
 *
 */
@Schema(name="用户对象")
@Data
@EqualsAndHashCode(of = {"userId"}, callSuper = false)
@ToString
public class User extends BasicEntity implements Serializable{
	
	private static final long serialVersionUID = -3683386137654425948L;

	@Schema(name="用户id")
	private String userId;
	@Schema(name="用户名")
	@Pattern(regexp = "^[0-9a-zA-Z]{3,30}$", message = "用户名必须由3至30位英文字母或数字组成")
	private String username;
	@Schema(name="原密码")
	private String oldPassword;
	@Schema(name="密码")
	@NotEmpty(message = "密码不能为空")
	private String password;
	@Schema(name="密码加密盐值")
	private String salt;
	@Schema(name="昵称")
	private String nickname;
	@Schema(name="电子邮箱")
	@Email(message = "电子邮箱格式不正确")
	private String email;
	@Schema(name="电子邮箱已确认")
	private Boolean hasEmailConfirmed;
	@Schema(name="手机")
	@Pattern(regexp = "^((1\\d{10})|())$", message = "手机号码格式不正确")
	private String cellphone;
	@Schema(name="头像Url")
	private String portraitUrl;
	@Schema(name="性别")
	private String gender;
	@Schema(name="账户状态")
	private Integer status;
	@Schema(name="是否使用系统头像")
	private Boolean useSystemPortrait;
	@Schema(name="使用中的系统头像名")
	private Boolean systemPortraitName;
	@Schema(name="自定义头像url")
	private Boolean customPortraitUrl;
	@Schema(name="注册时间")
	private Date registerTime;

	@Schema(name="操作验证码（包括：登录以及其他需要验证码的操作）")
	private String verifyingCode;

	@Schema(name="所有身份")
	private List<Identity> identities = new LinkedList<Identity>();
	@Schema(name="当前身份id")
	private String currentIdentityId;
	@Schema(name="当前权限Codes")
	private List<String> currentPrivilegeCodes = new LinkedList<String>();
	@Schema(name="角色ids，用于不能删除自己的角色的判断")
	private List<String> currentRoleIds = new LinkedList<String>();

	@Schema(name="扩展字段，不存入数据库，用于向前端传递好友备注信息")
	private String friendMemo;
	@Schema(name="客户端类型，不存入数据库，用于扩展")
	private String clientType;

	private HashMap<String, Identity> idToIdentityMap = new HashMap<String, Identity>();

	@JsonIgnore
	public boolean hasPrivilege(String privilegeCode) {
		if (currentPrivilegeCodes == null) {
			return false;
		} else {
			return currentPrivilegeCodes.contains(privilegeCode);
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

	@JsonIgnore
	public boolean setCurrentIdentityId(String currentIdentityId) {
		if(idToIdentityMap.get(currentIdentityId)!=null) {
			this.currentIdentityId = currentIdentityId;
			return true;
		}else {
			return false;
		}
	}

	@JsonIgnore
	public void setIdentities(List<Identity> identities) {
		this.identities = identities;
		idToIdentityMap.clear();
		if(identities!=null) {
			for (Identity identity : identities) {
				idToIdentityMap.put(identity.getIdentityId(), identity);
			}
		}
	}
}