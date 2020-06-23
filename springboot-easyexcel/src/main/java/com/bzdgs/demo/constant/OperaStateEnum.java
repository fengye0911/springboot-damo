package com.bzdgs.demo.constant;

/**
 * ClassName: OperaStateEnum.java
 * Description: 日志操作枚举
 *
 * @author bairong@cloudwalk.cn
* @version 1.1.0
 * @date 2020/2/27 10:34
 */
public enum OperaStateEnum {
    /**
     * 操作状态
     */
    SUCC(0, "成功"), FAIL(1, "失败");

    /**
     * 是否成功标记
     */
    private int code;

    /**
     * 日志描述
     */
    private String value;

    /**
     * 私有的构造方法
     *
     * @param code
     * @param value
     */
    private OperaStateEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 通过编号获取日志类型描述
     *
     * @param code
     * @return
     */
    public static String getValue(int code) {
        for (OperaStateEnum lt : OperaStateEnum.values()) {
            if (lt.getCode() == code) {
                return lt.getValue();
            }
        }

        return "";
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
