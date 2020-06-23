package com.bzdgs.demo.annotation;

import com.bzdgs.demo.constant.OperaTypeEnum;

import java.lang.annotation.*;

/**
 * 操作日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {

    /**
     * 模块编号
     */
    String model();

    /**
     * 操作类型
     */
    OperaTypeEnum type();

    /**
     * 日志类型 默认操作日志
     */
    LogType logType() default LogType.OPERA;

    /**
     * 描述
     */
    String desc();

    enum LogType {
        /**
         * 日志类型
         */
        LOGIN(1, "登录日志"),
        OPERA(2, "操作日志"),
        SYSTEM(3, "系统日志");

        /**
         * 日志编号
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
        private LogType(int code, String value) {
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
            for (LogType lt : LogType.values()) {
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
}
