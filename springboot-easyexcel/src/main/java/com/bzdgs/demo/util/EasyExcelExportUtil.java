package com.bzdgs.demo.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.bzdgs.demo.model.ExcelData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ClassName: EasyExcelExportUtil.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/22
 */
public class EasyExcelExportUtil {

    public static byte[] exportExcel(ExcelData data, ByteArrayOutputStream out) throws IOException {
        ExcelWriter excelWriter = EasyExcel.write(out, ExcelData.class).build();
        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet(data.getName()).build();
        return null;
    }
}
