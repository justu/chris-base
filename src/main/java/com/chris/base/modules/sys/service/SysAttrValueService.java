package com.chris.base.modules.sys.service;

import com.chris.base.modules.sys.entity.SysAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性值表
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Aug 28.18
 */
public interface SysAttrValueService {
	
	SysAttrValueEntity queryObject(Integer id);
	
	List<SysAttrValueEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysAttrValueEntity sysAttrValue);
	
	void update(SysAttrValueEntity sysAttrValue);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
