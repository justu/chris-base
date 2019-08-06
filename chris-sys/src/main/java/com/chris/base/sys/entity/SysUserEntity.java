package com.chris.base.sys.entity;

import com.chris.base.common.model.SysUpdateInfo;
import com.chris.base.shiro.model.ShiroUser;

import java.util.List;

/**
 * 系统用户
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2016年9月18日 上午9:28:55
 */
public class SysUserEntity extends SysUpdateInfo implements ShiroUser {
    /**
     * 用户ID
     */
    private Long userId;

    private String username;

    private String password;

    /**
     * 盐
     */
    private String salt;

    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 角色ID列表
     */
    private List<Long> roleIdList;

    private String openId;

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getSalt() {
        return salt;
    }

    @Override
    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    @Override
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    @Override
    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    @Override
    public String getOpenId() {
        return openId;
    }

    @Override
    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
