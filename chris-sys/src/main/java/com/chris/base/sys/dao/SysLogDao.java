package com.chris.base.sys.dao;

import com.chris.base.common.dao.BaseDao;
import com.chris.base.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2017-03-08 10:40:56
 */
@Mapper
public interface SysLogDao extends BaseDao<SysLogEntity> {
	
}
