package com.chris.base.common.wx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.*;
import com.chris.base.common.wx.dto.WXMsgTempSendDTO;
import com.chris.base.common.wx.exception.InvalidAccessTokenException;
import com.chris.base.common.wx.service.WXService;
import com.chris.base.common.wx.util.WXConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wxService")
@Slf4j
public class WXServiceImpl implements WXService {

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public CommonResponse sendWXMsgTemp(WXMsgTempSendDTO msg, String appId, String secret) {
        this.validate(msg);
        String accessToken = this.getAccessToken(appId, secret);
        String resp = this.restTemplateUtils.httpPostMediaTypeJson(WXConstants.Urls.SEND_MSG_TEMP.replace("#{accessToken}", accessToken), String.class, msg);
        JSONObject result = JSONObject.parseObject(resp);
        if (this.isReqSuccessful(result)) {
            return CommonResponse.ok();
        } else {
            return CommonResponse.error(result.getString("errmsg"));
        }
    }

    private boolean isReqSuccessful(JSONObject result) {
        return ValidateUtils.equals(WXConstants.REQ_SUCCESSFUL, result.getIntValue("errcode"));
    }


    @Override
    public String getAccessToken(String appId, String secret) {
        String accessToken = this.redisUtils.get(RedisKeys.APP_ACCESS_TOKEN, 3600);
        if (ValidateUtils.isNotEmptyString(accessToken)) {
            return accessToken;
        }
        String resp = this.restTemplateUtils.httpGet(WXConstants.Urls.GET_ACCESS_TOKEN.replace("#{appId}", appId).replace("#{secret}", secret), String.class);
        JSONObject result = JSONObject.parseObject(resp);
        if (this.isReqSuccessful(result)) {
            accessToken = result.getString("access_token");
            this.redisUtils.set(RedisKeys.APP_ACCESS_TOKEN, accessToken, 3600);
            return accessToken;
        }
        throw new InvalidAccessTokenException(result.getString("errmsg"));
    }

    private void validate(WXMsgTempSendDTO msg) {
        if (ValidateUtils.isEmptyString(msg.getTouser())) {
            throw new CommonException("发送对象openid为空");
        }
        if (ValidateUtils.isEmptyString(msg.getTemplate_id())) {
            throw new CommonException("模板ID为空");
        }
        if (ValidateUtils.isEmptyString(msg.getPage())) {
            throw new CommonException("跳转页面为空");
        }
        if (ValidateUtils.isEmptyString(msg.getForm_id())) {
            throw new CommonException("表单ID为空");
        }
    }
}
