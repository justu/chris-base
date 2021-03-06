package com.chris.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.chris.base.modules.sms.config.SMSFactory;
import com.chris.base.modules.sms.entity.SMSEntity;
import com.chris.base.modules.sms.entity.SysSmsSendRecordEntity;
import com.chris.base.modules.sms.service.SysSmsSendRecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author hewei
 * @version V.1.0.0
 * @date Created in 11:33 2018/11/12
 */
public class SendSMSUtils {

    public static SendSmsResponse sendSms(SMSEntity smsEntity) {
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        DefaultAcsClient acsClient = SMSFactory.build();

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();

        // 必填:待发送手机号
        request.setPhoneNumbers(smsEntity.getMobile());
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(SMSFactory.getConfig().getSignName());
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsEntity.getTemplateCode());

        String templateParam = smsEntity.getTemplateParam();
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        if (Constant.SMSType.VERIFY.equals(smsEntity.getSmsType())) {
            JSONObject jsonObject = JSONObject.parseObject(templateParam);
            int length = Integer.parseInt(jsonObject.get("code").toString());
            String validationCode = VerifyCodeUtils.getValidationCode(length);
            jsonObject.put("code", validationCode);
            templateParam = jsonObject.toJSONString();
            HttpServletRequest httpServletRequest = HttpContextUtils.getHttpServletRequest();
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(smsEntity.getMobile(), validationCode);
            JSONObject jsonObj = JSONObject.parseObject(smsEntity.getTemplateParam());
            jsonObj.put("code", validationCode);
            smsEntity.setTemplateParam(jsonObj.toJSONString());
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
            if(sendSmsResponse.getCode() != null && Constant.SMS_SEND_OK.equals(sendSmsResponse.getCode())) {
                //请求成功
                saveRecord(smsEntity);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return sendSmsResponse;
    }

    private static void saveRecord(SMSEntity smsEntity) {
        SysSmsSendRecordEntity sysSmsSendRecordEntity = new SysSmsSendRecordEntity();
        sysSmsSendRecordEntity.setMobile(smsEntity.getMobile());
        sysSmsSendRecordEntity.setTemplateParam(smsEntity.getTemplateParam());
        sysSmsSendRecordEntity.setTemplateCode(smsEntity.getTemplateCode());
        SysSmsSendRecordService sysSmsSendRecordService = SpringContextUtils.getBean("sysSmsSendRecordService", SysSmsSendRecordService.class);
        sysSmsSendRecordService.save(sysSmsSendRecordEntity);
    }

    public static String getVerifyCode(String mobile) {
        HttpServletRequest httpServletRequest = HttpContextUtils.getHttpServletRequest();
        Object mobileAttr = httpServletRequest.getSession().getAttribute(mobile);
        return ValidateUtils.isEmpty(mobileAttr)? null : mobileAttr.toString();
    }

    public static String getVerifyCode4App(String mobile, String tempCode) {
        SysSmsSendRecordService sysSmsSendRecordService = SpringContextUtils.getBean("sysSmsSendRecordService", SysSmsSendRecordService.class);
        String json = sysSmsSendRecordService.queryParamByMobile(mobile, tempCode);
        if (ValidateUtils.isNotEmptyString(json)) {
            JSONObject jsonObj = JSONObject.parseObject(json);
            return jsonObj.getString("code");
        }
        return "";
    }
}
