package com.chris.base.common.wx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.CommonResponse;
import com.chris.base.common.utils.RestTemplateUtils;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.common.wx.dto.WXMsgTempSendDTO;
import com.chris.base.common.wx.service.WXService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wxService")
@Slf4j
public class WXServiceImpl implements WXService {

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Override
    public CommonResponse sendWXMsgTemp(WXMsgTempSendDTO msg) {
        this.validate(msg);
        String resp = this.restTemplateUtils.httpPostMediaTypeJson(MSG_TEMP_URL, String.class, msg);
        JSONObject result = JSONObject.parseObject(resp);
        if (ValidateUtils.equals("ok", result.getString("errmsg"))) {
            return CommonResponse.ok();
        } else {
            return CommonResponse.error(result.getString("errmsg"));
        }
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
