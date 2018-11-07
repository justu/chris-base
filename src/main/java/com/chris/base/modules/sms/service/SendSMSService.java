package com.chris.base.modules.sms.service;

import com.chris.base.common.utils.CommonResponse;

/**
 * @author hewei
 * @version V.1.0.0
 * @date Created in 22:08 2018/11/7
 */
public interface SendSMSService {

    CommonResponse sendSms(String mobile, String templateParam, String templateCode);
}
