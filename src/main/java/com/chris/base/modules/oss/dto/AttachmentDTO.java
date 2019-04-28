package com.chris.base.modules.oss.dto;

import java.io.Serializable;

/**
 * 附件DTO
 */
public class AttachmentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //附件ID
    private Long id;
    //附件名称
    private String name;
    //附件URL
    private String url;

    public AttachmentDTO() {
    }

    public AttachmentDTO(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
