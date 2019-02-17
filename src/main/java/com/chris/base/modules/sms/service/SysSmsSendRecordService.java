package com.chris.base.modules.sms.service;

import com.chris.base.modules.sms.entity.SysSmsSendRecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 短信服务下发记录表
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Nov 11.18
 */
public interface SysSmsSendRecordService {
	
	SysSmsSendRecordEntity queryObject(Long id);
	
	List<SysSmsSendRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysSmsSendRecordEntity sysSmsSendRecord);
	
	void update(SysSmsSendRecordEntity sysSmsSendRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	String queryParamByMobile(String mobile, String tempCode);
}
