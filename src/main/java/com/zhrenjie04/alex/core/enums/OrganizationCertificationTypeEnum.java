package com.zhrenjie04.alex.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.zhrenjie04.alex.core.BasicEnum;

/**
 * 该机构认证类型
 */
public enum OrganizationCertificationTypeEnum implements BasicEnum {
    Certificated("Certificated","Certificated","已认证"),
    NotCertificated("NotCertificated","NotCertificated","未认证");

    @JsonValue
    private String frontendCode;
    private String dbCode;
    private String description;
    private OrganizationCertificationTypeEnum(String frontendCode, String dbCode, String description) {
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
