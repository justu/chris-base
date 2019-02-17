package com.chris.base.modules.app.controller;


import com.chris.base.common.utils.CommonResponse;
import com.chris.base.modules.app.annotation.Login;
import com.chris.base.modules.app.annotation.LoginUser;
import com.chris.base.modules.app.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * APP测试接口
 *
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2017-03-23 15:47
 */
@RestController
@RequestMapping("/app")
@Api("APP测试接口")
public class ApiTestController {

    @Login
    @GetMapping("userInfo")
    @ApiOperation("获取用户信息")
    public CommonResponse userInfo(@LoginUser UserEntity user){
        return CommonResponse.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public CommonResponse userInfo(@RequestAttribute("userId") Integer userId){
        return CommonResponse.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public CommonResponse notToken(){
        return CommonResponse.ok().put("msg", "无需token也能访问。。。");
    }

}
