package com.chris.base.modules.app.controller;


import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.*;
import com.chris.base.modules.app.cache.AppLoginUser;
import com.chris.base.modules.app.cache.AppLoginUserCacheUtils;
import com.chris.base.modules.app.dto.AppRequestDTO;
import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.app.service.UserService;
import com.chris.base.modules.app.utils.JwtUtils;
import com.chris.base.modules.sys.entity.SysMenuEntity;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/app")
@Api("APP用户接口")
@Slf4j
public class ApiUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RestTemplateUtils restTemplateUtils;

    private static final String WX_URL = "https://api.weixin.qq.com/sns/jscode2session";
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

    @PostMapping("loginOrRegister")
    @ApiOperation("用户登录或注册")
    public CommonResponse loginOrRegister(@RequestBody UserEntity user){
        this.validateParams(user, true);
        String verifyCode = SendSMSUtils.getVerifyCode4App(user.getMobile(), Constant.SMSTemplateCode.REGISTER_VERIFY_CODE.getTemplateCode());
        if (!ValidateUtils.equals(verifyCode, user.getVerifyCode())) {
            return CommonResponse.error("验证码输入不正确！");
        }
        // 验证手机号是否已经注册
        UserEntity resultUser = this.userService.queryByMobile(user.getMobile());
        if (ValidateUtils.isEmpty(resultUser)) {
            // 手机号未注册则进行注册
            userService.registerUserWithoutPwd(user);
            return this.doLoginAndGenerateToken(user);
        } else {
            // 手机号已经注册则直接获取用户菜单权限
            return this.doLoginAndGenerateToken(resultUser);
        }
    }

    private CommonResponse doLoginAndGenerateToken(UserEntity user) {
        Map<String, Object> map = this.doGenerateAppToken(user.getUserId());
        List<SysMenuEntity> userMenus = this.userService.queryUserMenusByOpenId(user.getOpenId());
        map.put("menus", userMenus);
        map.put("roleId", user.getRoleId());
        AppLoginUserCacheUtils.addAppLoginUser(user.getOpenId(), new AppLoginUser(user));
        return CommonResponse.ok(map);
    }

    @PostMapping("register")
    @ApiOperation("用户注册")
    public CommonResponse register(@RequestBody UserEntity user){
        this.validateParams(user, true);
        String verifyCode = SendSMSUtils.getVerifyCode4App(user.getMobile(), Constant.SMSTemplateCode.REGISTER_VERIFY_CODE.getTemplateCode());
        if (!ValidateUtils.equals(verifyCode, user.getVerifyCode())) {
            return CommonResponse.error("验证码输入不正确！");
        }
        userService.registerUser(user);
        return this.doLoginAndGenerateToken(user);
    }

    private void validateParams(UserEntity user, boolean isValidateOpenId) {
        if (ValidateUtils.isEmptyString(user.getMobile())) {
            throw new CommonException("手机号不能为空！");
        }
        if (ValidateUtils.isEmptyString(user.getVerifyCode())) {
            throw new CommonException("验证码不能为空！");
        }
        if (isValidateOpenId) {
            if (ValidateUtils.isEmptyString(user.getOpenId())) {
                throw new CommonException("openId不能为空！");
            }
        }
    }

    @PostMapping("appEnter")
    @ApiOperation("进入APP时操作")
    public CommonResponse appEnter(@RequestBody AppRequestDTO appRequestDTO) {
        log.error("appEnter 请求参数JSON = {} ", JSONObject.toJSONString(appRequestDTO));
        //首先获取到openId
        String respStr = this.restTemplateUtils.httpGetUrlVariable(WX_URL, String.class, this.convertReq2Map(appRequestDTO));
        JSONObject resp = JSONObject.parseObject(respStr);
        if (ValidateUtils.isNotEmpty(resp.get("errmsg"))) {
            log.error("appEnter 请求失败！{}", resp);
            return CommonResponse.error("openId不存在！");
        }
        String openId = resp.getString("openid");
        // 根据 openId 查询用户信息
        UserEntity user = this.userService.queryUserByOpenId(openId);
        if (ValidateUtils.isNotEmpty(user)) {
            Map<String, Object> map = this.doGenerateAppToken(user.getUserId());
            // TODO 获取用户微信端菜单
            List<SysMenuEntity> userMenus = this.userService.queryUserMenusByOpenId(openId);
            map.put("menus", userMenus);
            map.put("roleId", user.getRoleId());
            AppLoginUserCacheUtils.addAppLoginUser(openId, new AppLoginUser(user));
            return CommonResponse.ok(map).put("openid", openId);
        } else {
            // TODO 当前微信用户未注册，跳转到登录页
            return CommonResponse.error(Constant.ErrorCode.UNREGISTER,"用户未注册！").put("openid", openId);
        }
    }

    private Map<String, Object> convertReq2Map(AppRequestDTO appRequestDTO) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("appid", appRequestDTO.getAppId());
        paramMap.put("secret", appRequestDTO.getSecret());
        paramMap.put("js_code", appRequestDTO.getJsCode());
        paramMap.put("grant_type", appRequestDTO.getGrantType());
        return paramMap;
    }

}
