package com.chris.base.modules.oss.dao;

import com.chris.base.modules.oss.entity.SysAttachmentEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 附件信息
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Sep 16.18
 */
@Mapper
public interface SysAttachmentDao extends BaseDao<SysAttachmentEntity> {

    void updateBatch(List<SysAttachmentEntity> sysAttachments);

    List<SysAttachmentEntity> queryAttachmentsByCondition(SysAttachmentEntity param);

    void updateAttachmentsStatus2DeletedByObjId(Long resId);
}
