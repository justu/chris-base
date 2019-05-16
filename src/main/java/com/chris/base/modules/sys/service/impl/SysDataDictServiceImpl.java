package com.chris.base.modules.sys.service.impl;

import com.chris.base.modules.sys.dao.SysDataDictDao;
import com.chris.base.modules.sys.dto.SysDataDictDTO;
import com.chris.base.modules.sys.entity.SysDataDictEntity;
import com.chris.base.modules.sys.service.SysDataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysDataDictService")
public class SysDataDictServiceImpl implements SysDataDictService {
	@Autowired
	private SysDataDictDao sysDataDictDao;
	
	@Override
	public SysDataDictEntity queryObject(Integer id){
		return sysDataDictDao.queryObject(id);
	}
	
	@Override
	public List<SysDataDictEntity> queryList(Map<String, Object> map){
		return sysDataDictDao.queryList(map);
	}

	@Override
	public List<SysDataDictDTO> queryDataDictDtoList(Map<String, Object> map){
		return sysDataDictDao.queryDataDictDtoList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysDataDictDao.queryTotal(map);
	}
	
	@Override
	public void save(SysDataDictEntity sysDataDict){
		sysDataDictDao.save(sysDataDict);
	}
	
	@Override
	public void update(SysDataDictEntity sysDataDict){
		sysDataDictDao.update(sysDataDict);
	}
	
	@Override
	public void delete(Integer id){
		sysDataDictDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysDataDictDao.deleteBatch(ids);
	}
	
}
