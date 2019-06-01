package com.chris.base.modules.sys.controller;

import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.*;
import com.chris.base.modules.sys.dto.SysDataDictDTO;
import com.chris.base.modules.sys.entity.SysDataDictEntity;
import com.chris.base.modules.sys.service.SysDataDictService;
import com.google.common.collect.ImmutableMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 数据字典表
 *
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Sep 18.18
 */
@RestController
@RequestMapping("/sys/sysdatadict")
public class SysDataDictController {
    @Autowired
    private SysDataDictService sysDataDictService;

    /**
     * 获取所有数据字典列表
     */
    @RequestMapping("/listAll")
    @RequiresPermissions("sys:sysdatadict:list")
    public CommonResponse listAll() {

        List<SysDataDictEntity> dataDictList = GlobalDataUtils.getDataDictList();

        return CommonResponse.ok().put("dataDictList", dataDictList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysdatadict:list")
    public CommonResponse list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<SysDataDictEntity> sysDataDictList = sysDataDictService.queryList(query);
        int total = sysDataDictService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(sysDataDictList, total, query.getLimit(), query.getPage());

        return CommonResponse.ok().put("page", pageUtil);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysdatadict:info")
    public CommonResponse info(@PathVariable("id") Integer id) {
        SysDataDictEntity sysDataDict = sysDataDictService.queryObject(id);

        return CommonResponse.ok().put("sysDataDict", sysDataDict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysdatadict:save")
    public CommonResponse save(@RequestBody SysDataDictEntity sysDataDict) {
        sysDataDictService.save(sysDataDict);

        return CommonResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysdatadict:update")
    public CommonResponse update(@RequestBody SysDataDictEntity sysDataDict) {
        sysDataDictService.update(sysDataDict);

        return CommonResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysdatadict:delete")
    public CommonResponse delete(@RequestBody Integer[] ids) {
        sysDataDictService.deleteBatch(ids);

        return CommonResponse.ok();
    }

    @RequestMapping("/list.notoken")
    public CommonResponse queryByType(@RequestParam String type) {
        if (ValidateUtils.isEmptyString(type)) {
            throw new CommonException("类别为空");
        }
        List<SysDataDictDTO> dataDictList = this.sysDataDictService.queryDataDictDtoList(ImmutableMap.of("type", type));
        return CommonResponse.ok().put("list", dataDictList);
    }

}
