package com.chris.base.modules.app.cache;

import java.io.Serializable;

/**
 * App 登录用户信息
 * @author chris
 * @since Jan 12.19
 */
public class AppLoginUser implements Serializable {
    private String username;

    private String mobile;

    private Long roleId;

    public AppLoginUser(String username, String mobile, Long roleId) {
        this.username = username;
        this.mobile = mobile;
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}