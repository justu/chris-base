package com.chris.base.modules.sms.entity;

import com.chris.base.common.utils.Constant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hewei
 * @version V.1.0.0
 * @date Created in 14:05 2018/11/12
 */
@Getter
@Setter
public class SMSEntity {

    private String mobile;

    private Constant.SMSType smsType;

    private String templateParam;

    private String templateCode;

}
