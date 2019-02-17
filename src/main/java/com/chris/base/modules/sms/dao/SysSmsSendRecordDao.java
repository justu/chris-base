package com.chris.base.modules.sms.dao;

import com.chris.base.modules.sms.entity.SysSmsSendRecordEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 短信服务下发记录表
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Nov 11.18
 */
@Mapper
public interface SysSmsSendRecordDao extends BaseDao<SysSmsSendRecordEntity> {

    String queryParamByMobile(@Param("mobile") String mobile, @Param("tempCode") String tempCode);

}
