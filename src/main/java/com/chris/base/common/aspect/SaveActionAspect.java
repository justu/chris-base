package com.chris.base.common.aspect;

import com.chris.base.common.model.SysUpdateInfo;
import com.chris.base.common.utils.DateUtils;
import com.chris.base.common.utils.ShiroUtils;
import com.chris.base.common.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SaveActionAspect {

    @Before("execution(* com.chris.base.*.*.service.impl.*(..))")
    public void beforeSave(JoinPoint joinPoint) throws Throwable {
        if (ValidateUtils.isNotEmptyArray(joinPoint.getArgs())) {
            Object obj = joinPoint.getArgs()[0];
            this.setUpdateInfoValue(obj);
        }
    }

    private void setUpdateInfoValue(Object obj) {
        if (ValidateUtils.isNotEmpty(obj) && obj instanceof SysUpdateInfo) {
            SysUpdateInfo updateInfo = (SysUpdateInfo) obj;
            updateInfo.setCreateTime(DateUtils.currentDate());
            updateInfo.setCreateUserId(ShiroUtils.getUserId());
        }
    }
}
