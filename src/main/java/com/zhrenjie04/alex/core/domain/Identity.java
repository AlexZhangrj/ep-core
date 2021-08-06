package com.zhrenjie04.alex.core.domain;

import com.zhrenjie04.alex.core.enums.GroupTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 张人杰
 */
@Schema(name="身份对象")
@Data
@EqualsAndHashCode(of = {"identityId"}, callSuper = false)
@ToString
public class Identity implements Serializable{
	
	private static final long serialVersionUID = 6547730311451225436L;

	private String identityId;
	private String userId;
	private GroupTypeEnum groupType;
	private String groupId;
	private String identityName;
	private Integer status;

	/** 只用于前端显示 */
	private String groupName;
	/** 只用于前端显示 */
	private String groupShortName;
}
