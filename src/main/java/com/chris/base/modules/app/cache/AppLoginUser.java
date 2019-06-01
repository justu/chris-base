package com.chris.base.modules.app.cache;

import com.chris.base.modules.app.entity.UserEntity;

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

    private Long userId;

    private String token;

    public AppLoginUser(UserEntity user, String token) {
        this.username = user.getUsername();
        this.mobile = user.getMobile();
        this.roleId = user.getRoleId();
        this.userId = user.getUserId();
        this.token = token;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
