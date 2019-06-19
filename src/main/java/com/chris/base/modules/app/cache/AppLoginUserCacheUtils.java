package com.chris.base.modules.app.cache;

import com.chris.base.common.utils.SpringContextUtils;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.app.service.UserService;
import com.google.common.collect.Maps;

import java.util.Map;

public class AppLoginUserCacheUtils {
    public static volatile Map<String, AppLoginUser> appLoginUserMap = Maps.newConcurrentMap();

    public static volatile Map<String, Boolean> reloginMap = Maps.newConcurrentMap();

    public static void addAppLoginUser(String openId, AppLoginUser appLoginUser) {
        appLoginUserMap.put(openId, appLoginUser);
    }

    public static AppLoginUser getAppLoginUser(String openId) {
        AppLoginUser appLoginUser = appLoginUserMap.get(openId);
        if (ValidateUtils.isEmpty(appLoginUser)) {
            UserService userService = (UserService) SpringContextUtils.getBean("userService");
            UserEntity user = userService.queryUserByOpenId(openId);
            if (ValidateUtils.isNotEmpty(user)) {
                appLoginUser = new AppLoginUser(user, null);
                appLoginUserMap.put(openId, appLoginUser);
            }
        }
        return appLoginUser;
    }

}
