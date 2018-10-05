package com.chris.base.modules.sys.controller;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.modules.sys.entity.SysAttrEntity;
import com.chris.base.modules.sys.service.SysAttrService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 属性表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Aug 28.18
 */
@RestController
@RequestMapping("/sys/sysattr")
public class SysAttrController {
	@Autowired
	private SysAttrService sysAttrService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:sysattr:list")
	public CommonResponse list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysAttrEntity> sysAttrList = sysAttrService.queryList(query);
		int total = sysAttrService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysAttrList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:sysattr:info")
	public CommonResponse info(@PathVariable("id") Integer id){
		SysAttrEntity sysAttr = sysAttrService.queryObject(id);
		
		return CommonResponse.ok().put("sysAttr", sysAttr);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:sysattr:save")
	public CommonResponse save(@RequestBody SysAttrEntity sysAttr){
		sysAttrService.save(sysAttr);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:sysattr:update")
	public CommonResponse update(@RequestBody SysAttrEntity sysAttr){
		sysAttrService.update(sysAttr);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:sysattr:delete")
	public CommonResponse delete(@RequestBody Integer[] ids){
		sysAttrService.deleteBatch(ids);
		
		return CommonResponse.ok();
	}
	
}
