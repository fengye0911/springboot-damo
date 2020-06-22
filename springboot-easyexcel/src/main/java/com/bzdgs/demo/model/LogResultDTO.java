package com.bzdgs.demo.model;

import lombok.Data;

/**
 * ClassName: LogResultDTO.java
 * Description: 日志查询结果DTO
 *
 * @author bairong@cloudwalk.cn
* @version 1.1.0
 * @date 2020/2/27 10:34
 */
@Data
public class LogResultDTO {
    /**
     * 主键ID
     */
    private String appLog;

    /**
     * 操作员ID
     */
    private String userId;

    /**
     * 机构编号
     */
    private String orgId;

    /**
     * 电话
     */
    private String phoneNumber;

    /**
     * 警员编号
     */
    private String workCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 操作时间
     */
    private Long operTime;

    /**
     * 模块编号 详见数据字段 MODULE_CODE
     */
    private String moduleCode;

    /**
     * 类型(ADD 新增、EDIT 编辑、DELETE 删除、 SELECT 查询、SEARCH 检索)
     */
    private String type;

    /**
     * 日志类型
     */
    private Short logType;

    /**
     * IP
     */
    private String ip;

    /**
     * URL
     */
    private String url;

    /**
     * 耗时单位毫秒
     */
    private Integer timeConsum;

    /**
     * 是否成功 0 成功 1 失败
     */
    private Short isSuccess;

    /**
     * 描述信息
     */
    private String opDesc;

    /**
     * 菜单ID
     */
    private String apiReferer;
    private String apiRefererName;

}
