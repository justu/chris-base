package com.chris.base.modules.app.controller;


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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (ValidateUtils.isEmptyString(user.getMobile())) {
            return CommonResponse.error("手机号不能为空！");
        }
        if (ValidateUtils.isEmptyString(user.getPassword())) {
            return CommonResponse.error("密码不能为空！");
        }
        Map<String, Object> map = this.generateAppToken(user);


        return CommonResponse.ok(map);
    }

    private Map<String, Object> generateAppToken(UserEntity user) {
        //用户登录
        long userId = this.userService.login(user.getMobile(), user.getPassword());
        return this.doGenerateAppToken(userId);


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
        if (ValidateUtils.isEmptyString(user.getMobile())) {
            return CommonResponse.error("手机号不能为空！");
        }
        if (ValidateUtils.isEmptyString(user.getPassword())) {
            return CommonResponse.error("密码不能为空！");
        }
        userService.registerUser(user);
        Map<String, Object> map = this.doGenerateAppToken(user.getUserId());
        return CommonResponse.ok(map);
    }

    @PostMapping("appEnter")
    @ApiOperation("进入APP时操作")
    public CommonResponse appEnter(String openId) {
        // 根据 openId 查询用户信息
        UserEntity user = this.userService.queryUserByOpenId(openId);
        if (ValidateUtils.isNotEmpty(user)) {
            // TODO 获取用户微信端菜单
            List<SysMenuEntity> userMenus = this.userService.queryUserMenusByOpenId(openId);
            return CommonResponse.ok().put("menus", userMenus);
        } else {
            // TODO 当前微信用户未注册，跳转到登录页
            return CommonResponse.error("用户未注册！");
        }
    }

}
