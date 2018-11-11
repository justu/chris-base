package com.chris.base.modules.sms.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.chris.base.modules.sms.SMSType;

/**
 * @author hewei
 * @version V.1.0.0
 * @date Created in 22:08 2018/11/7
 */
public interface SendSMSService {

    SendSmsResponse sendSms(String mobile, SMSType smsType, String templateParam, String templateCode);

    String getVerifyCode(String mobile);
}
