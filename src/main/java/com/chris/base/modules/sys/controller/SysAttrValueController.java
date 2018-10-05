package com.chris.base.modules.sys.controller;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.modules.sys.entity.SysAttrValueEntity;
import com.chris.base.modules.sys.service.SysAttrValueService;
import com.chris.base.common.utils.PageUtils;
import com.chris.base.common.utils.Query;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 属性值表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Mar 22.18
 */
@RestController
@RequestMapping("/sys/sysattrvalue")
public class SysAttrValueController {
	@Autowired
	private SysAttrValueService sysAttrValueService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:sysattrvalue:list")
	public CommonResponse list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysAttrValueEntity> sysAttrValueList = sysAttrValueService.queryList(query);
		int total = sysAttrValueService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysAttrValueList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{attrValueId}")
	@RequiresPermissions("sys:sysattrvalue:info")
	public CommonResponse info(@PathVariable("attrValueId") Integer attrValueId){
		SysAttrValueEntity sysAttrValue = sysAttrValueService.queryObject(attrValueId);
		
		return CommonResponse.ok().put("sysAttrValue", sysAttrValue);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:sysattrvalue:save")
	public CommonResponse save(@RequestBody SysAttrValueEntity sysAttrValue){
		sysAttrValueService.save(sysAttrValue);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:sysattrvalue:update")
	public CommonResponse update(@RequestBody SysAttrValueEntity sysAttrValue){
		sysAttrValueService.update(sysAttrValue);
		
		return CommonResponse.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:sysattrvalue:delete")
	public CommonResponse delete(@RequestBody Integer[] attrValueIds){
		sysAttrValueService.deleteBatch(attrValueIds);
		
		return CommonResponse.ok();
	}
	
}
