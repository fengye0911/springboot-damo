package com.bzdgs.demo.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bzdgs.demo.model.ExcelData;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.poi.ss.usermodel.BorderStyle.THIN;

/**
 * ClassName: EasyExcelUtil.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/22
 */
public class EasyExcelUtil {

    /**
     * 导出表头必填字段标红色
     * @param outputStream 输入流
     * @param dataList 导入数据
     * @param headList 表头列表
     * @param sheetName sheetname
     * @param cellWriteHandlers
     */
    public static void writeExcelWithModel(OutputStream outputStream,
                                           List<? extends Object> dataList,
                                           List<String> headList,
                                           String sheetName,
                                           CellWriteHandler... cellWriteHandlers) {
        List<List<String>> list = new ArrayList<>();
        if(headList != null){
            headList.forEach(h -> list.add(Collections.singletonList(h)));
        }

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 单元格策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(outputStream).head(list).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy);
        if(null != cellWriteHandlers && cellWriteHandlers.length>0){
            for(int i = 0 ; i < cellWriteHandlers.length;i++){
                excelWriterSheetBuilder.registerWriteHandler(cellWriteHandlers[i]);
            }
        }
        // 开始导出
        excelWriterSheetBuilder.doWrite(dataList);
    }
    /**
     * 导出表头必填字段标红色
     * @param outputStream 输入流
     * @param dataList 导入数据
     * @param headList 表头列表
     * @param sheetName sheetname
     * @param cellWriteHandlers
     */
    public static void writeExcelWithModel(OutputStream outputStream,
                                           List<? extends Object> dataList,
                                           Class<? extends Object> headList,
                                           String sheetName,
                                           CellWriteHandler... cellWriteHandlers) {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 单元格策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(outputStream,headList).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy);
        if(null != cellWriteHandlers && cellWriteHandlers.length>0){
            for(int i = 0 ; i < cellWriteHandlers.length;i++){
                excelWriterSheetBuilder.registerWriteHandler(cellWriteHandlers[i]);
            }
        }
        // 开始导出
        excelWriterSheetBuilder.doWrite(dataList);
    }


    public static byte[] exportExcel(ExcelData data,ByteArrayOutputStream out) throws IOException {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("simsun");
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 11);
        contentWriteFont.setColor(IndexedColors.BLACK.index);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        //设置 自动换行
        contentWriteCellStyle.setWrapped(false);

        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        //设置边框样式
        contentWriteCellStyle.setBorderLeft(THIN);
        contentWriteCellStyle.setBorderTop(THIN);
        contentWriteCellStyle.setBorderRight(THIN);
        contentWriteCellStyle.setBorderBottom(THIN);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(out)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet(data.getName()).build();
        try {
            List<List<String>> titles = data.getTitles();
            if (!CollectionUtils.isEmpty(titles)){
                //如果标题行不为空，则写入
                excelWriter.write(titles,writeSheet);
            }
            excelWriter.write(data.getRows(),writeSheet);
        } finally {
            excelWriter.finish();
        }
        return out.toByteArray();
    }
}
