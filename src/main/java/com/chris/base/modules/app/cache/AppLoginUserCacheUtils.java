package com.chris.base.modules.app.cache;

import com.google.common.collect.Maps;

import java.util.Map;

public class AppLoginUserCacheUtils {
    public static volatile Map<String, AppLoginUser> appLoginUserMap = Maps.newConcurrentMap();

    public static void addAppLoginUser(String openId,AppLoginUser appLoginUser) {
        appLoginUserMap.put(openId, appLoginUser);
    }

    public static AppLoginUser getAppLoginUser(String openId) {
        return appLoginUserMap.get(openId);
    }

}
