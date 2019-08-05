package com.chris.base.common.exception;

import com.chris.base.common.model.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class CommonExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(CommonException.class)
	public CommonResponse handleCommonException(CommonException e){
		return CommonResponse.error(e.getCode(), e.getMsg());
	}


	@ExceptionHandler(Exception.class)
	public CommonResponse handleException(Exception e){
		logger.error(e.getMessage(), e);
		return CommonResponse.error(e.getMessage());
	}
}
