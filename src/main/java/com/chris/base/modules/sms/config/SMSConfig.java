package com.chris.base.modules.sms.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hewei
 * @version V.1.0.0
 * @date Created in 22:25 2018/11/7
 */
@Getter
@Setter
public class SMSConfig {

    /**
     * 短信API产品名称（短信产品名固定，无需修改）Dysmsapi
     */
    private String product;
    /**
     * 短信API产品域名（接口地址固定，无需修改）dysmsapi.aliyuncs.com
     */
    private String domain;
    /**
     * 你的accessKeyId
     */
    private String accessKeyId;
    /**
     * 你的accessKeySecret
     */
    private String accessKeySecret;
    /**
     * 你的签名
     */
    private String signName;

}
