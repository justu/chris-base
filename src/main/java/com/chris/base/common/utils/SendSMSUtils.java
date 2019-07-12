package com.chris.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.model.VerifySMSParam;
import com.chris.base.modules.sms.config.SMSFactory;
import com.chris.base.modules.sms.entity.SMSEntity;
import com.chris.base.modules.sms.entity.SysSmsSendRecordEntity;
import com.chris.base.modules.sms.service.SysSmsSendRecordService;
import com.google.common.base.Verify;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author hewei
 * @version V.1.0.0
 * @date Created in 11:33 2018/11/12
 */
@Slf4j
public class SendSMSUtils {

    private static final long SMS_EXP_TIME = 180;

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
            validateSMS(smsEntity);
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
            VerifySMSParam verifySMSParam = new VerifySMSParam(smsEntity.getMobile(), validationCode, DateUtils.currentDate(), IPUtils.getIpAddr());
            getRedisUtils().set(getVerifySMSKey(smsEntity.getMobile(), smsEntity.getTemplateCode()), verifySMSParam, SMS_EXP_TIME);
            getRedisUtils().set(getVerifySMSKey(verifySMSParam.getIpAddr(), smsEntity.getTemplateCode()), verifySMSParam, SMS_EXP_TIME);
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

    private static String getVerifySMSKey(String value, String tempCode) {
        return RedisKeys.Prefix.VERIFY_CODE + value + "_" + tempCode;
    }

    private static void validateSMS(SMSEntity smsEntity) {
        // TODO 1分钟之内只能发一次
        Date curDate = DateUtils.currentDate();
        VerifySMSParam verifySMSParam = getRedisUtils().get(getVerifySMSKey(smsEntity.getMobile(), smsEntity.getTemplateCode()), VerifySMSParam.class);
        if (ValidateUtils.isNotEmpty(verifySMSParam) && DateUtils.getBetweenMinutes(verifySMSParam.getSendDate(), curDate) < 1) {
            throw new CommonException("同一手机号1分钟只能下发一次短信");
        }
        String ipAddr = IPUtils.getIpAddr();
        if (log.isDebugEnabled()) {
            log.debug("手机号 = {}, IP地址 = {}", smsEntity.getMobile(), ipAddr);
        }
        verifySMSParam = getRedisUtils().get(getVerifySMSKey(ipAddr, smsEntity.getTemplateCode()), VerifySMSParam.class);
        if (ValidateUtils.isNotEmpty(verifySMSParam) && DateUtils.getBetweenMinutes(verifySMSParam.getSendDate(), curDate) < 1) {
            throw new CommonException("同一IP地址1分钟只能下发一次短信");
        }
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
        VerifySMSParam verifySMSParam = getRedisUtils().get(getVerifySMSKey(mobile, tempCode), VerifySMSParam.class);
        if (ValidateUtils.isEmpty(verifySMSParam)) {
            return null;
        }
        return verifySMSParam.getCode();
    }

    private static RedisUtils getRedisUtils() {
        return SpringContextUtils.getBean("redisUtils", RedisUtils.class);
    }
}
