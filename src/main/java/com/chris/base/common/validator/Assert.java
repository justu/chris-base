package com.chris.base.common.validator;

import com.chris.base.common.exception.CommonException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2017-03-23 15:50
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CommonException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CommonException(message);
        }
    }
}
