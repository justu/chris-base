package com.chris.base.common.wx.service;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.wx.dto.WXMsgTempSendDTO;

public interface WXService {

    String MSG_TEMP_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";

    CommonResponse sendWXMsgTemp(WXMsgTempSendDTO msg);
}
