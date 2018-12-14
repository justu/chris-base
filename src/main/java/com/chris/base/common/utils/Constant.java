package com.chris.base.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 *
 * @author chris
 * @email 258321511@qq.com
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;

    public static final String TEMP_URL = "/smartpark/img/#{fileType}";

    public static final Map<String, String> FILE_TYPE_MAP = new HashMap<>();

    public static final int VERIFY_CODE_LENGTH = 4;

    public static final String SMS_SEND_OK = "OK";

    public static final String ROOT_NODE_ID = "-1";

    static {
        FILE_TYPE_MAP.put("doc", "word.png");
        FILE_TYPE_MAP.put("docx", "word.png");
        FILE_TYPE_MAP.put("xls", "excel.png");
        FILE_TYPE_MAP.put("xlsx", "excel.png");
        FILE_TYPE_MAP.put("csv", "excel.png");
        FILE_TYPE_MAP.put("jpg", "image2.png");
        FILE_TYPE_MAP.put("jpeg", "image2.png");
        FILE_TYPE_MAP.put("gif", "image2.png");
        FILE_TYPE_MAP.put("png", "image2.png");
        FILE_TYPE_MAP.put("pdf", "pdf.png");
        FILE_TYPE_MAP.put("mp4", "video3.png");
        FILE_TYPE_MAP.put("avi", "video3.png");
    }

    /**
     * 菜单类型
     *
     * @author chris
     * @email 258321511@qq.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chris
     * @email 258321511@qq.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum FileType {
        DOCUMENT("文档"), IMAGE("图片"), VIDEO("视频");

        private String value;

        FileType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public interface Symbol {
        String SLASH = "/";
        String DOT = ".";
        String UNDERLINE = "_";
        String COMMA = ",";
    }

    public interface WXRole {
        long VISITOR = 1L;
        long STAFF = 2L;
        long ADMIN = 3L;
    }

    public interface UserSource {
        int SYSTEM_USER = 1;
        int WX_USER = 2;
    }

    public enum SMSType {

        /**
         * 通知类短信
         */
        NOTICE,

        /**
         * 验证类短信
         */
        VERIFY
    }

    public enum SMSTemplateCode {

        /**
         * 预约审核失败短信模板
         */
        RESERVATION_FAIL("SMS_150182172"),
        /**
         * 预约审核成功短信模板
         */
        RESERVATION_SUCCESS("SMS_150172102"),
        /**
         * 预约处理短信模板
         */
        RESERVATION_HANDLE("SMS_150172100"),
        /**
         * 预约验证码短信模板
         */
        RESERVATION_VERIFY_CODE("SMS_150172084"),
        /**
         * 注册验证码短信模板
         */
        REGISTER_VERIFY_CODE("SMS_150182159"),
        /**
         * 访客来访提醒被访人
         */
        RESERVATION_NOTICE("SMS_152505669");

        private String templateCode;

        SMSTemplateCode(String templateCode) {
            this.templateCode = templateCode;
        }

        public String getTemplateCode() {
            return templateCode;
        }
    }

    public interface ErrorCode {
        /***
         * 未注册
         **/
        int UNREGISTER = 510;
    }

    public interface Keys {
        String OBJ_ID = "objId";
        String OBJ_SOURCE = "objSource";
    }

}
