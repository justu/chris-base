package com.chris.base.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chris
 * @since Apr 20.18
 */
public class CommonResponse<T> extends HashMap<String, Object> implements Serializable {
    private String code;
    private String msg;
    private T data;

    private CommonResponse(int code, String msg) {
        this.code = code + "";
        this.msg = msg;
    }

    public static CommonResponse ok() {
        return (new CommonResponse()).setCode(HttpStatus.SC_OK + "").setMsg("success");
    }
    public static CommonResponse ok(String msg) {
        return (new CommonResponse()).setCode(HttpStatus.SC_OK + "").setMsg(msg);
    }

    public static CommonResponse ok(Map<String, Object> map) {
        CommonResponse commonResponse = (new CommonResponse()).setCode(HttpStatus.SC_OK + "").setMsg("success");
        commonResponse.putAll(map);
        return commonResponse;
    }

    public static CommonResponse error(String errorMsg) {
        return (new CommonResponse()).setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR + "").setMsg(errorMsg);
    }
    public static CommonResponse error(Integer code, String errorMsg) {
        return (new CommonResponse()).setCode(code + "").setMsg(errorMsg);
    }

    public static CommonResponse getLoginErrorResponse() {
        return (new CommonResponse()).setCode(HttpStatus.SC_UNAUTHORIZED + "").setMsg("auth_error");
    }

    public CommonResponse() {
    }

    public CommonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public CommonResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public CommonResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public CommonResponse setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public CommonResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        try {
            String e = "CommonResponse{code=\'" + this.code + '\'' + ", msg=\'" + this.msg + '\'' + ", data=" + (new ObjectMapper()).writeValueAsString(this.data) + '}';
            return e;
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
