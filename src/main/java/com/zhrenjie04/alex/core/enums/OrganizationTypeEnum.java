package com.zhrenjie04.alex.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.zhrenjie04.alex.core.BasicEnum;

/**
 * 机构类型
 */
public enum OrganizationTypeEnum implements BasicEnum {
    GroupCompany("GroupCompany","GroupCompany","集团公司"),
    Company("Company","Company","公司"),
    Department("Department","Department","部门"),
    ProjectDepartment("Project-Department","Project-Department","项目部"),
    Team("Team","Team","小组");

    @JsonValue
    private String frontendCode;
    private String dbCode;
    private String description;
    private OrganizationTypeEnum(String frontendCode, String dbCode, String description) {
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
