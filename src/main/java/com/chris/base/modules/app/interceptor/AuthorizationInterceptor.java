package com.chris.base.modules.app.interceptor;


import com.chris.base.common.utils.CacheDataUtils;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.annotation.Login;
import com.chris.base.modules.app.cache.AppLoginUser;
import com.chris.base.modules.app.cache.AppLoginUserCacheUtils;
import com.chris.base.modules.app.utils.JwtUtils;
import com.chris.base.common.exception.CommonException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CacheDataUtils cacheDataUtils;

    public static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String tokenValidateFlag = this.cacheDataUtils.getConfigValueByKey("TOKEN_VALIDATE_FLAG");
        if (ValidateUtils.equals(tokenValidateFlag, "false")) {
            return true;
        }
        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        } else {
            return true;
        }

        if (annotation == null) {
            return true;
        }

        //获取用户凭证
        String token = request.getHeader(jwtUtils.getHeader());
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(jwtUtils.getHeader());
        }

        //凭证为空
        if (StringUtils.isBlank(token)) {
            throw new CommonException(jwtUtils.getHeader() + "不能为空", HttpStatus.UNAUTHORIZED.value());
        }

        Claims claims = jwtUtils.getClaimByToken(token);
        if (claims == null || jwtUtils.isTokenExpired(claims.getExpiration())) {
            throw new CommonException(jwtUtils.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
        }

        this.forceReloginIfNeed(token);

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(USER_KEY, Long.parseLong(claims.getSubject()));

        return true;
    }

    /**
     * 强制重新登录（如果需要）
     * @param token
     */
    private void forceReloginIfNeed(String token) {
        if (ValidateUtils.isNotEmpty(AppLoginUserCacheUtils.reloginMap)) {
            String removeKey = "";
            for (String key : AppLoginUserCacheUtils.reloginMap.keySet()) {
                AppLoginUser appUser = AppLoginUserCacheUtils.getAppLoginUser(key);
                if (ValidateUtils.isNotEmpty(appUser) && ValidateUtils.equals(appUser.getToken(), token)) {
                    removeKey = key;
                    break;
                }
            }
            if (ValidateUtils.isNotEmptyString(removeKey)) {
                AppLoginUserCacheUtils.reloginMap.remove(removeKey);
                throw new CommonException(jwtUtils.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
            }
        }
    }
}
