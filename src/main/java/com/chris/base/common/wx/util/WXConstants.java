package com.chris.base.common.wx.util;

public class WXConstants {

    public static final int REQ_SUCCESSFUL = 0;

    public interface Urls {
        String SEND_MSG_TEMP = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=#{accessToken}";

        String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=#{appId}&secret=#{secret}";
    }
}
