package com.chris.base.modules.oss.controller;

import com.chris.base.modules.oss.cloud.CloudStorageConfig;
import com.chris.base.modules.oss.cloud.OSSFactory;
import com.chris.base.modules.oss.entity.SysAttachmentEntity;
import com.chris.base.modules.oss.service.SysAttachmentService;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.utils.*;
import com.chris.base.common.validator.ValidatorUtils;
import com.chris.base.common.validator.group.AliyunGroup;
import com.chris.base.common.validator.group.QcloudGroup;
import com.chris.base.common.validator.group.QiniuGroup;
import com.chris.base.modules.sys.service.SysConfigService;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 文件上传
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2017-03-25 12:13:26
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
	@Autowired
	private SysAttachmentService sysAttachmentService;
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public CommonResponse list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysAttachmentEntity> attachmentList = this.sysAttachmentService.queryList(query);
		int total = this.sysAttachmentService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(attachmentList, total, query.getLimit(), query.getPage());
		
		return CommonResponse.ok().put("page", pageUtil);
	}


    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public CommonResponse config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        return CommonResponse.ok().put("config", config);
    }


	/**
	 * 保存云存储配置信息
	 */
	@RequestMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public CommonResponse saveConfig(@RequestBody CloudStorageConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getType() == Constant.CloudService.QINIU.getValue()){
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		}else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}
		

        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

		return CommonResponse.ok();
	}
	

	/**
	 * 上传文件
	 */
	@RequestMapping("/upload.notoken")
	public CommonResponse upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
		if (file.isEmpty()) {
			throw new CommonException("上传文件不能为空");
		}
		SysAttachmentEntity attachmentEntity = this.sysAttachmentService.doUploadFile(file, null);
		return CommonResponse.ok().put("url", attachmentEntity.getUrl()).put("attachmentObj", attachmentEntity);
	}

	@RequestMapping("/downLoad")
	public CommonResponse downLoad(HttpServletRequest request, HttpServletResponse response) throws Exception{
		SysAttachmentEntity attachmentEntity = this.sysAttachmentService.queryObject(Long.valueOf(request.getParameter("id")));
		String name = new String(attachmentEntity.getName().getBytes("gbk"),"iso-8859-1");
		response.setHeader("content-disposition", "attachment; filename=" + name);
		response.setCharacterEncoding("gbk");
		OSSFactory.build().download(attachmentEntity.getUrl(), response);
		return CommonResponse.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public CommonResponse delete(Long id){
		this.sysAttachmentService.delete(id);
		return CommonResponse.ok();
	}

}
