package com.chris.base.modules.oss.service.impl;

import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.Constant;
import com.chris.base.modules.oss.cloud.OSSFactory;
import com.chris.base.modules.oss.dao.SysAttachmentDao;
import com.chris.base.modules.oss.entity.SysAttachmentEntity;
import com.chris.base.modules.oss.service.SysAttachmentService;
import com.chris.base.common.utils.ValidateUtils;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service("sysAttachmentService")
public class SysAttachmentServiceImpl implements SysAttachmentService {
	@Autowired
	private SysAttachmentDao sysAttachmentDao;
	
	@Override
	public SysAttachmentEntity queryObject(Long id){
		return sysAttachmentDao.queryObject(id);
	}
	
	@Override
	public List<SysAttachmentEntity> queryList(Map<String, Object> map){
		return sysAttachmentDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysAttachmentDao.queryTotal(map);
	}
	
	@Override
	public void save(SysAttachmentEntity sysAttachment){
		sysAttachmentDao.save(sysAttachment);
	}
	
	@Override
	public void update(SysAttachmentEntity sysAttachment){
		sysAttachmentDao.update(sysAttachment);
	}
	
	@Override
	public void delete(Long id){
		sysAttachmentDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysAttachmentDao.deleteBatch(ids);
	}

	@Override
	public void updateBatch(List<SysAttachmentEntity> sysAttachments) {
		this.sysAttachmentDao.updateBatch(sysAttachments);
	}

	@Override
	public List<SysAttachmentEntity> queryAttachmentsByCondition(SysAttachmentEntity param) {
		List<SysAttachmentEntity> attachments = this.sysAttachmentDao.queryAttachmentsByCondition(param);
		if (ValidateUtils.isNotEmptyCollection(attachments)) {
			attachments.forEach(item -> item.generateTempURL());
		}
		return attachments;
	}

	@Override
	public void updateAttachmentsStatus2DeletedByResIds(Long[] resIds) {
		for (int i = 0; i < resIds.length; i++) {
			this.sysAttachmentDao.updateAttachmentsStatus2DeletedByObjId(resIds[i]);
		}
	}

	@Override
	public SysAttachmentEntity doUploadFile(MultipartFile file, Map<String, Object> params) throws IOException {
		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileType = this.getFileType(suffix.substring(1));
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

		//保存文件信息
		SysAttachmentEntity attachmentEntity = new SysAttachmentEntity();
		attachmentEntity.setUrl(url);
		attachmentEntity.setName(file.getOriginalFilename());
		attachmentEntity.setSuffixName(suffix.substring(1).toLowerCase());
		attachmentEntity.setType(fileType);
		attachmentEntity.generateTempURL();
		if (ValidateUtils.isNotEmpty(params)) {
		    if (params.containsKey(Constant.Keys.OBJ_ID)) {
		        attachmentEntity.setObjId(Long.valueOf(params.get(Constant.Keys.OBJ_ID).toString()));
            }
            if (params.containsKey(Constant.Keys.OBJ_SOURCE)) {
		        attachmentEntity.setObjSource(params.get(Constant.Keys.OBJ_SOURCE).toString());
            }
        }
		this.save(attachmentEntity);
		return attachmentEntity;
	}

	private String getFileType(String suffix) {
		ImmutableList<String> docTypes = ImmutableList.of("doc", "docx", "xls", "xlsx", "csv", "pdf");
		if (docTypes.contains(suffix.toLowerCase())) {
			return Constant.FileType.DOCUMENT.getValue();
		}
		ImmutableList<String> imgTypes = ImmutableList.of("png", "jpg", "jpeg", "gif", "bmp");
		if (imgTypes.contains(suffix.toLowerCase())) {
			return Constant.FileType.IMAGE.getValue();
		}
		ImmutableList<String> videoTypes = ImmutableList.of("mp4", "avi");
		if (videoTypes.contains(suffix.toLowerCase())) {
			return Constant.FileType.VIDEO.getValue();
		}
		throw new CommonException("不支持的文件类型[" + suffix + "]");
	}
}
