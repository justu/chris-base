package com.chris.base.common.exception;

import com.chris.base.common.utils.CommonResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author chris
 * @email 258321511@qq.com
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

	@ExceptionHandler(DuplicateKeyException.class)
	public CommonResponse handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return CommonResponse.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public CommonResponse handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return CommonResponse.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public CommonResponse handleException(Exception e){
		logger.error(e.getMessage(), e);
		return CommonResponse.error(e.getMessage());
	}
}
