package com.chris.base.sys.controller;

import com.chris.base.common.model.CommonResponse;
import com.chris.base.common.utils.*;
import com.chris.base.common.annotation.SysLog;
import com.chris.base.sys.entity.SysConfigEntity;
import com.chris.base.sys.service.SysConfigService;
import com.chris.base.sys.utils.CacheDataUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 系统参数信息
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private CacheDataUtils cacheDataUtils;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public CommonResponse list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysConfigEntity> configList = sysConfigService.queryList(query);
		int total = sysConfigService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public CommonResponse info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.queryObject(id);
		
		return CommonResponse.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public CommonResponse save(@RequestBody SysConfigEntity config){
//		ValidateUtils.validatedParams(config);
		List<SysConfigEntity> configList = this.cacheDataUtils.getConfigList();
		configList.add(config);
		sysConfigService.save(config);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public CommonResponse update(@RequestBody SysConfigEntity config){
//		ValidatorUtils.validateEntity(config);
		List<SysConfigEntity> configList = this.cacheDataUtils.getConfigList();
		configList.forEach(item -> {
			if (ValidateUtils.equals(config.getKey(), item.getKey())) {
				item.setValue(config.getValue());
			}
		});
		sysConfigService.update(config);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public CommonResponse delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}

}
