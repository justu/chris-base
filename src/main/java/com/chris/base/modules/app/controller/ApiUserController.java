package com.chris.base.modules.app.controller;


import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.app.service.UserService;
import com.chris.base.modules.app.utils.JwtUtils;
import com.chris.base.common.validator.Assert;
import com.chris.base.modules.sys.entity.SysMenuEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author chris
 * @email 258321511@qq.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app")
@Api("APP用户接口")
public class ApiUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("用户登录")
    public CommonResponse login(@RequestBody UserEntity user){
        this.validateParams(user, false);
        Map<String, Object> map = this.generateAppToken(user);

        return CommonResponse.ok(map);
    }

    private Map<String, Object> generateAppToken(UserEntity user) {
        //用户登录
        UserEntity resultUser = this.userService.login(user.getMobile(), user.getPassword());
        Map<String, Object> map = this.doGenerateAppToken(resultUser.getUserId());
        List<SysMenuEntity> userMenus = this.userService.queryUserMenusByOpenId(resultUser.getOpenId());
        map.put("menus", userMenus);
        return map;
    }

    private Map<String, Object> doGenerateAppToken(long userId) {
        //生成token
        String token = this.jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        return map;
    }

    @PostMapping("register")
    @ApiOperation("用户注册")
    public CommonResponse register(@RequestBody UserEntity user){
        this.validateParams(user, true);
        userService.registerUser(user);
        Map<String, Object> map = this.doGenerateAppToken(user.getUserId());
        List<SysMenuEntity> userMenus = this.userService.queryUserMenusByOpenId(user.getOpenId());
        map.put("menus", userMenus);
        return CommonResponse.ok(map);
    }

    private void validateParams(UserEntity user, boolean isValidateOpenId) {
        if (ValidateUtils.isEmptyString(user.getMobile())) {
            throw new CommonException("手机号不能为空！");
        }
        if (ValidateUtils.isEmptyString(user.getPassword())) {
            throw new CommonException("密码不能为空！");
        }
        if (isValidateOpenId) {
            if (ValidateUtils.isEmptyString(user.getOpenId())) {
                throw new CommonException("openId不能为空！");
            }
        }
    }

    @GetMapping("appEnter")
    @ApiOperation("进入APP时操作")
    public CommonResponse appEnter(String openId) {
        // 根据 openId 查询用户信息
        UserEntity user = this.userService.queryUserByOpenId(openId);
        if (ValidateUtils.isNotEmpty(user)) {
            Map<String, Object> map = this.doGenerateAppToken(user.getUserId());
            // TODO 获取用户微信端菜单
            List<SysMenuEntity> userMenus = this.userService.queryUserMenusByOpenId(openId);
            map.put("menus", userMenus);
            return CommonResponse.ok(map);
        } else {
            // TODO 当前微信用户未注册，跳转到登录页
            return CommonResponse.error("用户未注册！");
        }
    }

}
