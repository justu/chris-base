package com.chris.base.sys.dao;

import com.chris.base.common.dao.BaseDao;
import com.chris.base.sys.entity.SysAttrValueEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 属性值表
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Aug 28.18
 */
@Mapper
public interface SysAttrValueDao extends BaseDao<SysAttrValueEntity> {
	
}
