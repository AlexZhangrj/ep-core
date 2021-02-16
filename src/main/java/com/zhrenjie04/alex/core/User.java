package com.zhrenjie04.alex.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	
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
	@Schema(name="真实姓名")
	private String realname;
	@Schema(name="昵称")
//	@NotEmpty(message = "昵称不能为空")
	private String nickname;
	@Schema(name="电子邮箱")
	@Email(message = "电子邮箱格式不正确")
	private String email;
	@Schema(name="电子邮箱已确认")
	private Boolean hasEmailConfirmed;
	@Schema(name="电子邮箱验证码")
	private String emailVerifyCode;
	@Schema(name="手机")
	@Pattern(regexp = "^((1\\d{10})|())$", message = "手机号码格式不正确")
	private String cellphone;
	@Schema(name="头像Url")
	private String portraitUrl;
	@Schema(name="微信号")
	private String weixin;
	@Schema(name="生日")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@Schema(name="性别")
	private String gender;
	@Schema(name="是否已锁定")
	private Boolean isLocked;
	@Schema(name="是否已删除")
	private Boolean isDeleted;
	@Schema(name="最后登录IP")
	private String lastLoginIp;
	@Schema(name="最后登录时间")
	@JsonSerialize(using=AlexTimestampSerializer.class)
	private Date lastLoginTime;
	@Schema(name="jobs")
	private List<Identity> identities = new LinkedList<Identity>();
	@Schema(name="当前身份id")
	private String currentIdentityId;
	@Schema(name="权限Codes")
	private List<String> currentPrivilegeCodes = new LinkedList<String>();
	@Schema(name="角色ids，用于不能删除自己的角色的判断")
	private List<String> currentRoleIds = new LinkedList<String>();
	@Schema(name="账号所属集团id")
	private String groupId;
	@Schema(name="验证码传输字段")
	private String captcha;
	@Schema(name="是否修改密码")
	private Boolean notChangePassword;
	@Schema(name="扩展字段，用于向前端传递好友备注信息")
	private String friendMemo;
	@Schema(name="扩展字段，用于显示BigDecimal的处理过程")
	private BigDecimal bigDecimalTag=new BigDecimal("2.9");
	@Schema(name="客户端类型")
	private String clientType;

	private HashMap<String, Identity> idToIdentityMap = new HashMap<String, Identity>();

	private Date createdTime;
	private String creatorId;
	private String creatorName;
	private Date lastModifiedTime;
	private String lastModifierId;
	private String lastModifierName;

	@Schema(name="jwt-token失效时间")
	private Date jwtExpiredTime;
	
	@JsonIgnore
	public boolean hasPrivilege(String privilegeCode) {
		if (currentPrivilegeCodes == null) {
			return false;
		} else {
			return currentPrivilegeCodes.contains(privilegeCode);
		}
	}

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
	
}