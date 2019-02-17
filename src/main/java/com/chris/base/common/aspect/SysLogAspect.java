package com.chris.base.common.aspect;

import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.cache.AppLoginUser;
import com.chris.base.modules.app.cache.AppLoginUserCacheUtils;
import com.google.gson.Gson;
import com.chris.base.common.annotation.SysLog;
import com.chris.base.common.utils.HttpContextUtils;
import com.chris.base.common.utils.IPUtils;
import com.chris.base.modules.sys.entity.SysLogEntity;
import com.chris.base.modules.sys.entity.SysUserEntity;
import com.chris.base.modules.sys.service.SysLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2017年3月8日 上午11:07:35
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;
	
	@Pointcut("@annotation(com.chris.base.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLogEntity sysLog = new SysLogEntity();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args[0]);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));


		if (ValidateUtils.isNotEmpty(SecurityUtils.getSubject()) &&
				ValidateUtils.isNotEmpty(SecurityUtils.getSubject().getPrincipal())) {
			//用户名
			String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
			sysLog.setUsername(username);
		} else {
			String openId = request.getHeader("openId");
			AppLoginUser appLoginUser = AppLoginUserCacheUtils.getAppLoginUser(openId);
			if (ValidateUtils.isNotEmpty(appLoginUser)) {
				sysLog.setUsername(appLoginUser.getUsername());
			} else {
				// 小程序没有用户 session 信息，则暂时设置默认用户名
				sysLog.setUsername("admin");
			}
		}

		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());
		//保存系统日志
		sysLogService.save(sysLog);
	}
}
