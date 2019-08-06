package com.chris.base.shiro.model;

import java.util.List;

public interface ShiroUser{
    Long getUserId();

    void setUserId(Long userId);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getSalt();

    void setSalt(String salt);

    String getEmail();

    void setEmail(String email);

    String getMobile();

    void setMobile(String mobile);

    Integer getStatus();

    void setStatus(Integer status);

    List<Long> getRoleIdList();

    void setRoleIdList(List<Long> roleIdList);

    String getOpenId();

    void setOpenId(String openId);
}
