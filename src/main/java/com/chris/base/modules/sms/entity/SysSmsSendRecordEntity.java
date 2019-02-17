package com.chris.base.modules.sms.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 短信服务下发记录表
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @since Nov 11.18
 */
public class SysSmsSendRecordEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//短信下发记录ID
	private Long id;
	//手机号
	private String mobile;
	//下发内容json
	private String templateParam;
	//短信模板ID
	private String templateCode;
	//下发时间
	private Date createTime;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}
	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}

	public String getTemplateParam() {
		return templateParam;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateCode() {
		return templateCode;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}
}
