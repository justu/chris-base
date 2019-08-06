package com.chris.base.sys.dao;

import com.chris.base.common.dao.BaseDao;
import com.chris.base.sys.dto.SysDataDictDTO;
import com.chris.base.sys.entity.SysDataDictEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 数据字典表
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Sep 18.18
 */
@Mapper
public interface SysDataDictDao extends BaseDao<SysDataDictEntity> {

    List<SysDataDictDTO> queryDataDictDtoList(Map<String, Object> map);
}
