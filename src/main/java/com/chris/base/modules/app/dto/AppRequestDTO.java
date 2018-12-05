package com.chris.base.modules.app.dto;

import java.io.Serializable;

public class AppRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String appId;

    private String secret;

    private String jsCode;

    private String grantType = "authorization_code";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getJsCode() {
        return jsCode;
    }

    public void setJsCode(String jsCode) {
        this.jsCode = jsCode;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
