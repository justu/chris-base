package com.chris.base.modules.oss.service;

import com.chris.base.modules.oss.entity.SysAttachmentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 附件信息
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Sep 16.18
 */
public interface SysAttachmentService {
	
	SysAttachmentEntity queryObject(Long id);
	
	List<SysAttachmentEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysAttachmentEntity sysAttachment);
	
	void update(SysAttachmentEntity sysAttachment);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateBatch(List<SysAttachmentEntity> sysAttachments);

    List<SysAttachmentEntity> queryAttachmentsByCondition(SysAttachmentEntity attachmentEntity);

    void updateAttachmentsStatus2DeletedByResIds(Long[] resIds);

	SysAttachmentEntity doUploadFile(MultipartFile file, Map<String, Object> params) throws IOException;
}
