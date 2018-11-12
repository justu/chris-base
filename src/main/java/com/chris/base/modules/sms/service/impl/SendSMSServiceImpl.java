package com.chris.base.modules.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.chris.base.common.utils.Constant;
import com.chris.base.common.utils.SendSMSUtils;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.sms.entity.SMSEntity;
import com.chris.base.modules.sms.service.SendSMSService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SendSMSServiceImpl implements SendSMSService {

    @Override
    public SendSmsResponse sendSms(Map<String, Object> params) {
        SendSmsResponse sendSmsResponse = null;
        Object mobile = params.get("mobile");
        Object templateCode = params.get("templateCode");
        if (ValidateUtils.isNotEmpty(mobile) && ValidateUtils.isNotEmpty(templateCode)) {
            JSONObject templateParam = new JSONObject();
            templateParam.put("code", Constant.VERIFY_CODE_LENGTH);
            SMSEntity smsEntity = new SMSEntity();
            smsEntity.setMobile(mobile.toString());
            smsEntity.setSmsType(Constant.SMSType.VERIFY);
            smsEntity.setTemplateCode(templateCode.toString());
            smsEntity.setTemplateParam(templateParam.toJSONString());
            sendSmsResponse = SendSMSUtils.sendSms(smsEntity);
        }

        return sendSmsResponse;

    }

}
