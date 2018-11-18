package com.chris.base.modules.sms.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.sms.service.SendSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author hewei
 * @version V.1.0.0
 * @date Created in 15:19 2018/11/12
 */
@RestController
@RequestMapping("sys/sms")
public class SysSMSController {

    @Autowired
    private SendSMSService sendSMSService;

    /**
     * 发送验证码短信
     */
    @RequestMapping(value = "/sendVerifyCode.notoken", method = RequestMethod.POST)
    public CommonResponse sendVerifyCode(@RequestBody Map<String, Object> params) {
        SendSmsResponse sendSmsResponse = sendSMSService.sendSms(params);
        if(ValidateUtils.isEmpty(sendSmsResponse)){
            return CommonResponse.error("参数错误");
        }
        return CommonResponse.ok().setData(sendSmsResponse);
    }
}
