package com.zhrenjie04.alex.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.zhrenjie04.alex.core.BasicEnum;

/**
 * 群组类型：组织机构、普通群组
 */
public enum GroupTypeEnum implements BasicEnum {
    Organization("Organization","Organization","组织"),
    NormalCrowd("Normal-Crowd","Normal-Crowd","普通群组");

    @JsonValue
    private String frontendCode;
    private String dbCode;
    private String description;
    private GroupTypeEnum(String frontendCode,String dbCode,String description) {
        this.frontendCode=frontendCode;
        this.dbCode=dbCode;
        this.description=description;
    }
    public String getDescription() {
        return description;
    }
    @Override
    public Object getDbCode() {
        return dbCode;
    }

    @Override
    public String getFrontendCode() {
        return frontendCode;
    }
}
