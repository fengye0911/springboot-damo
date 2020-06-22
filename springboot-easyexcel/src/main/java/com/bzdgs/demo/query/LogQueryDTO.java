package com.bzdgs.demo.query;

import lombok.Data;

import java.util.List;

/**
 * ClassName: LogQuery.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/22
 */
@Data
public class LogQueryDTO {

    /**
     * 关键词
     */
    private String keyword;

    private int pageSize;

    private int currentPage;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户集合
     */
    private List<String> userIds;

    /**
     * 访问者IP
     */
    private String ip;
    /**
     * 被访者地址
     */
    private String url;

    /**
     * 模块编号
     */
    private String moduleCode;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 机构编号
     */
    private String orgId;

    /**
     * 操作开始时间
     */
    private Long operTimeB;

    /**
     * 操作结束时间
     */
    private Long operTimeE;

    /**
     * 操作人姓名
     */
    private String name;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 日志类型
     */
    private Short logType;

    /**
     * 工号
     */
    private String workCode;

    /**
     * 是否成功
     */
    private Short isSuccess;

    /**
     * 描述
     */
    private String desc;

    /**
     * 入参
     */
    private String inPara;

    /**
     * 出参
     */
    private String outPara;

    /**
     * 菜单ID
     */
    private String apiReferer;

}
