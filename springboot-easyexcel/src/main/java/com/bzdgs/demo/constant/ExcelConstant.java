package com.bzdgs.demo.constant;

/**
 * ClassName: ExcelConstant.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/22
 */
public class ExcelConstant {
    /**

     * 每个sheet存储的记录数 100W

     */

    public static final Integer PER_SHEET_ROW_COUNT = 1000000;

    /**

     * 每次向EXCEL写入的记录数(查询每页数据大小) 20W

     */

    public static final Integer PER_WRITE_ROW_COUNT = 200000;
}
