package com.chris.base.modules.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.chris.base.common.utils.ConfigConstant;
import com.chris.base.common.utils.Constant;
import com.chris.base.common.utils.HttpContextUtils;
import com.chris.base.common.utils.VerifyCodeUtils;
import com.chris.base.modules.sms.SMSConfig;
import com.chris.base.modules.sms.entity.SysSmsSendRecordEntity;
import com.chris.base.modules.sms.service.SendSMSService;
import com.chris.base.modules.sms.service.SysSmsSendRecordService;
import com.chris.base.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class SendSMSServiceImpl implements SendSMSService {

    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysSmsSendRecordService sysSmsSendRecordService;

    @Override
    public SendSmsResponse sendSms(String mobile, Constant.SMSType smsType, String templateParam, Constant.SMSTemplateCode templateCode) {

        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        SMSConfig config = sysConfigService.getConfigObject(ConfigConstant.SMS_CONFIG_KEY, SMSConfig.class);
        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", config.getAccessKeyId(), config.getAccessKeySecret());
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", config.getProduct(), config.getDomain());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        DefaultAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();

        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(config.getSignName());
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode.getTemplateCode());

        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        if (Constant.SMSType.VERIFY.equals(smsType)) {
            JSONObject jsonObject = JSONObject.parseObject(templateParam);
            int length = Integer.parseInt(jsonObject.get("code").toString());
            String validationCode = VerifyCodeUtils.getValidationCode(length);
            jsonObject.put("code", validationCode);
            templateParam = jsonObject.toJSONString();
            HttpServletRequest httpServletRequest = HttpContextUtils.getHttpServletRequest();
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(mobile, validationCode);
        }
        request.setTemplateParam(templateParam);

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        // hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            saveRecord(mobile, templateParam, templateCode.getTemplateCode());
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return sendSmsResponse;
    }

    @Override
    public String getVerifyCode(String mobile) {
        HttpServletRequest httpServletRequest = HttpContextUtils.getHttpServletRequest();
        HttpSession session = httpServletRequest.getSession();
        return (String) session.getAttribute(mobile);
    }

    private void saveRecord(String mobile, String templateParam, String templateCode) {
        SysSmsSendRecordEntity sysSmsSendRecordEntity = new SysSmsSendRecordEntity();
        sysSmsSendRecordEntity.setMobile(mobile);
        sysSmsSendRecordEntity.setTemplateParam(templateParam);
        sysSmsSendRecordEntity.setTemplateCode(templateCode);
        sysSmsSendRecordService.save(sysSmsSendRecordEntity);
    }
}
