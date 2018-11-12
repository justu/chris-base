package com.chris.base.modules.sms.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.chris.base.common.utils.ConfigConstant;
import com.chris.base.common.utils.SpringContextUtils;
import com.chris.base.modules.sys.service.SysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hewei
 * @version V.1.0.0
 * @date Created in 11:26 2018/11/12
 */
public final class SMSFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSFactory.class);

    private static SysConfigService sysConfigService;

    private static SMSConfig config;

    private static DefaultAcsClient acsClient;

    static {
        SMSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
        //获取云存储配置信息
        config = sysConfigService.getConfigObject(ConfigConstant.SMS_CONFIG_KEY, SMSConfig.class);
    }

    public static DefaultAcsClient build() {
        if (acsClient == null) {
            // 初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", config.getAccessKeyId(), config.getAccessKeySecret());
            try {
                DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", config.getProduct(), config.getDomain());
            } catch (ClientException e) {
                e.printStackTrace();
            }
            acsClient = new DefaultAcsClient(profile);
        }
        return acsClient;
    }

    public static SMSConfig getConfig() {
        return config;
    }
}
