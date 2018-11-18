package com.chris.base.modules.sms.service.impl;

import com.chris.base.modules.sms.dao.SysSmsSendRecordDao;
import com.chris.base.modules.sms.entity.SysSmsSendRecordEntity;
import com.chris.base.modules.sms.service.SysSmsSendRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysSmsSendRecordService")
public class SysSmsSendRecordServiceImpl implements SysSmsSendRecordService {
	@Autowired
	private SysSmsSendRecordDao sysSmsSendRecordDao;
	
	@Override
	public SysSmsSendRecordEntity queryObject(Long id){
		return sysSmsSendRecordDao.queryObject(id);
	}
	
	@Override
	public List<SysSmsSendRecordEntity> queryList(Map<String, Object> map){
		return sysSmsSendRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysSmsSendRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(SysSmsSendRecordEntity sysSmsSendRecord){
		sysSmsSendRecordDao.save(sysSmsSendRecord);
	}
	
	@Override
	public void update(SysSmsSendRecordEntity sysSmsSendRecord){
		sysSmsSendRecordDao.update(sysSmsSendRecord);
	}
	
	@Override
	public void delete(Long id){
		sysSmsSendRecordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysSmsSendRecordDao.deleteBatch(ids);
	}

	@Override
	public String queryParamByMobile(String mobile, String tempCode) {
		return this.sysSmsSendRecordDao.queryParamByMobile(mobile, tempCode);
	}
}
