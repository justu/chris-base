package com.chris.base.modules.sms;

/**
 * @author hewei
 * @version V.1.0.0
 * @date Created in 22:25 2018/11/7
 */
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
