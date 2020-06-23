package com.bzdgs.demo.constant;

/**
 * 操作类型枚举
 */
public enum OperaTypeEnum {

    ADD("ADD", "新增"), EDIT("EDIT", "修改"), DELETE("DELETE", "删除"), SELECT("SELECT", "查询"), SEARCH("SEARCH", "检索"), OTHER("OTHER", "其他"), UNKNOWN("UNKNOWN", "未知");

    private String key;

    private String value;

    OperaTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 通过key获取枚举
     *
     * @param key
     * @return
     */
    public static OperaTypeEnum get(String key) {
        if (key == null || key.length() == 0) {
            return UNKNOWN;
        }
        for (OperaTypeEnum operaTypeEnum : values()) {
            if (operaTypeEnum.getKey().equals(key)) {
                return operaTypeEnum;
            }
        }
        return UNKNOWN;
    }
}
