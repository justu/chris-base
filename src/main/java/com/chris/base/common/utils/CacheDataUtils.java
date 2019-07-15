package com.chris.base.common.utils;

import com.chris.base.modules.sys.entity.SysConfigEntity;
import com.chris.base.modules.sys.entity.SysDataDictEntity;
import com.chris.base.modules.sys.service.SysConfigService;
import com.chris.base.modules.sys.service.SysDataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component("cacheDataUtils")
public class CacheDataUtils {
    @Autowired
    private SysDataDictService sysDataDictService;

    @Autowired
    private SysConfigService sysConfigService;

    private List<SysDataDictEntity> dataDictList;

    private List<SysConfigEntity> configList;

    @PostConstruct
    public void init() {
        this.dataDictList = this.sysDataDictService.queryList(Collections.EMPTY_MAP);
        System.out.println("dataDictList size = " + dataDictList.size());
        this.configList = this.sysConfigService.queryAll();
        System.out.println("configList size = " + configList.size());
    }


    public List<SysDataDictEntity> getDataDictList() {
        return this.dataDictList;
    }

    public List<SysConfigEntity> getConfigList() {
        return configList;
    }

    public String getConfigValueByKey(String key) {
        if (ValidateUtils.isNotEmptyCollection(this.configList)) {
            SysConfigEntity config = this.configList.stream().filter(item -> ValidateUtils.equals(key, item.getKey())).findFirst().orElse(null);
            return ValidateUtils.isNotEmpty(config) ? config.getValue() : null;
        }
        return null;
    }

    public String getConfigValueByKey(String key, String defaultValue) {
        String value = getConfigValueByKey(key);
        return ValidateUtils.isNotEmptyString(value) ? value : defaultValue;
    }
}
