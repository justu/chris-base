package com.chris.base.common.wx.service;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.wx.dto.WXMsgTempSendDTO;

public interface WXService {

    CommonResponse sendWXMsgTemp(WXMsgTempSendDTO msg, String appId, String secret);

    String getAccessToken(String appId, String secret);
}
