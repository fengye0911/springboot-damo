package com.bzdgs.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.bzdgs.demo.annotation.OpLog;
import com.bzdgs.demo.constant.OperaStateEnum;
import com.bzdgs.demo.constant.OperaTypeEnum;
import com.bzdgs.demo.model.DemoData;
import com.bzdgs.demo.model.ExcelData;
import com.bzdgs.demo.model.LogResultDTO;
import com.bzdgs.demo.query.LogQueryDTO;
import com.bzdgs.demo.service.ILogService;
import com.bzdgs.demo.util.EasyExcelExportUtil;
import com.bzdgs.demo.util.FileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ClassName: demo.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EasyExcelExportTest {

    @Autowired
    private ILogService logService;

    @Test
    public void testLogService(){
        LogQueryDTO queryDTO = new LogQueryDTO();
        queryDTO.setCurrentPage(1);
        queryDTO.setPageSize(900000);
//        queryDTO.setOperTimeB(1592718587000L);
//        queryDTO.setOperTimeE(1592804986000L);
        Integer count = logService.count();
//        List list = logService.page(queryDTO);
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }



    /**
     * 针对105W以内的记录数可以调用该方法分多批次查出然后写入到EXCEL的一个SHEET中
     * 注意：
     * 每次查询出来的记录数量不宜过大，根据内存大小设置合理的每次查询记录数，不会内存溢出即可。
     * 数据量不能超过一个SHEET存储的最大数据量105W
     *
     * @throws IOException
     */
    @Test
    public void writeExcelOneSheetMoreWrite() throws IOException {

        // 生成EXCEL并指定输出路径
        OutputStream out = new FileOutputStream("E:\\temp\\withoutHead1.xlsx");
        ExcelWriterBuilder writerBuilder = new ExcelWriterBuilder();
        writerBuilder.file(out);
        ExcelWriter writer = writerBuilder.build();

        // 设置SHEET
        WriteSheet sheet = new WriteSheet();
        sheet.setSheetName("sheet1");

        // 设置标题
        WriteTable table = new WriteTable();
        List<List<String>> titles = new ArrayList<List<String>>();
        titles.add(Arrays.asList("操作员ID"));
        titles.add(Arrays.asList("警员编号"));
        titles.add(Arrays.asList("姓名"));
        titles.add(Arrays.asList("日志类型"));
        titles.add(Arrays.asList("操作时间"));
        titles.add(Arrays.asList("操作模块"));
        titles.add(Arrays.asList("操作类型"));
        titles.add(Arrays.asList("操作结果"));
        titles.add(Arrays.asList("操作员IP"));
        titles.add(Arrays.asList("耗时(ms)"));
        titles.add(Arrays.asList("描述"));
        table.setHead(titles);

        //总行数
        Integer count = logService.count();

        // 模拟分批查询：总记录数50条，每次查询20条，  分三次查询 最后一次查询记录数是10
        Integer totalRowCount = count;
        Integer pageSize = 20000;
        Integer writeCount = totalRowCount % pageSize == 0 ? (totalRowCount / pageSize) : (totalRowCount / pageSize + 1);

        // 注： 此处仅仅为了模拟数据，实用环境不需要将最后一次分开，合成一个即可， 参数为： currentPage = i+1;  pageSize = pageSize
        LogResultDTO logResultDTO = new LogResultDTO();
        LogQueryDTO logQueryDTO = new LogQueryDTO();
        List<LogResultDTO> resultDTOS;
        for (int i = 0; i < writeCount; i++) {
            logQueryDTO.setCurrentPage(i);
            logQueryDTO.setPageSize(pageSize);
            resultDTOS = logService.page(logQueryDTO);

//            resultDTOS.forEach(logResultDTO1 -> {
//                List<List<Object>> userList = new ArrayList<>();
//                    userList.add(Arrays.asList(
//                            logResultDTO.getUserId(),
//                            logResultDTO.getWorkCode(),
//                            logResultDTO.getName(),
//                            logResultDTO.getLogType(),
//                            logResultDTO.getOperTime(),
//                            logResultDTO.getModuleCode(),
//                            logResultDTO.getType(),
//                            logResultDTO.getIsSuccess(),
//                            logResultDTO.getIp(),
//                            logResultDTO.getTimeConsum(),
//                            logResultDTO.getOpDesc()));
//                writer.write(userList, sheet, table);
//            });
            writer.write(resultDTOS, sheet, table);
        }

        writer.finish();
    }

    @Test
    public void repeatWrite0(){
        String fileName = "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        String path= "E:\\temp\\";

        //获取日志总行数
        Integer count = logService.count();

        // 模拟分批查询：总记录数50条，每次查询20条，  分三次查询 最后一次查询记录数是10
        Integer totalRowCount = count;
        //一次查20000条数据
        Integer pageSize = 200000;
        Integer writeCount = totalRowCount % pageSize == 0 ? (totalRowCount / pageSize) : (totalRowCount / pageSize + 1);
        LogResultDTO logResultDTO = new LogResultDTO();
        LogQueryDTO logQueryDTO = new LogQueryDTO();
        List<LogResultDTO> resultDTOS;
        ExcelData excelData = new ExcelData();
        int length=0;
        byte[] bytesAll = new byte[length];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        long start = System.currentTimeMillis();
        System.out.println("开始导出日志"+start);
        for (int i = 0; i < writeCount; i++) {
            logQueryDTO.setCurrentPage(i+1);
            logQueryDTO.setPageSize(pageSize);
            resultDTOS = logService.page(logQueryDTO);
            if(i ==0){
                excelData.setName("操作日志记录");
                List<List<String>> titles = new ArrayList();
                List<String> title=new ArrayList<>();
                title.add("操作员ID");
                title.add("警员编号");
                title.add("姓名");
                title.add("日志类型");
                title.add("操作时间");
                title.add("操作模块");
                title.add("操作类型");
                title.add("操作结果");
                title.add("操作员IP");
                title.add("耗时（ms）");
                title.add("描述");
                titles.add(title);
                excelData.setTitles(titles);
            }
            List<List<Object>> rows = new ArrayList();
            if (CollectionUtils.isNotEmpty(resultDTOS)) {
                addRows(rows,resultDTOS);
            } else {
                List<Object> row = new ArrayList<>();
                row.add("没有数据");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                rows.add(row);
            }
            excelData.setRows(rows);
            try {
                byte[] bytes = EasyExcelExportUtil.exportExcel(excelData,out);
                bytesAll = byteMerger(bytesAll, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileUtil.getFileByBytes(bytesAll,path,fileName);
        System.out.println("导出完成"+(System.currentTimeMillis()-start));

    }

    private byte[] byteMerger(byte[] bt1,byte[] bt2){
        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }
    /**
     * 把日志记录放到excel表中
     * @param rows
     * @param logResultDtos
     */
    private void addRows(List rows,List<LogResultDTO> logResultDtos){
        //获取功能模块集合

//        List<ResourceResultDTO> resourceList = resourceManager.list(new ResourceQueryDTO());
//        Map<String,ResourceResultDTO> resourceMaps = resourceList.stream().collect(Collectors.toMap(ResourceResultDTO::getResourceId, Function.identity()));
        for (LogResultDTO log : logResultDtos) {
            List<Object> row = new ArrayList<>();
            row.add(assign(log.getUserId()));
            row.add(assign(log.getWorkCode()));
            row.add(assign(log.getName()));
            //翻译日志类型
            row.add(log.getLogType() == null ? "" : OpLog.LogType.getValue(log.getLogType()));
            row.add(DateFormatUtils.format(log.getOperTime(), "yyyy-MM-dd HH:mm:ss"));
            //翻译模块名称
            String menuName = StringUtils.EMPTY;
//            if(StringUtils.isNotBlank(log.getApiReferer())) {
//                ResourceResultDTO resource = resourceMaps.get(log.getApiReferer());
//                if(resource!=null) {
//                    menuName = resource.getResCanme();
//                }
//            }
            row.add(menuName);
            //分页操作类型
            row.add(OperaTypeEnum.get(log.getType()).getValue());
            //翻译操作结果
            row.add(log.getIsSuccess() == null ? "" : OperaStateEnum.getValue(log.getIsSuccess()));
            row.add(assign(log.getIp()));
            row.add(log.getTimeConsum() == null ? "" : log.getTimeConsum());
            row.add(StringUtils.isNotBlank(log.getOpDesc()) && log.getOpDesc().length() > 255 ? StringUtils.left(log.getOpDesc(), 255) : log.getOpDesc());
            rows.add(row);
        }
    }

    /**
     * 非空判断，不为空返回原值，否则返回""
     * @param values
     * @return
     */
    private String assign(String values){
        if(values==null){
            return "";
        }
        return values;
    }

    @Test
    public void repeatWrite(){
        // 方法1 如果写到同一个sheet
        String fileName = "E:\\temp\\" + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 这里 需要指定写用哪个class去写
            excelWriter = EasyExcel.write(fileName, LogResultDTO.class).build();

            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            long start = System.currentTimeMillis();
            System.out.println("开始导出"+start);
            //总行数
            Integer count = logService.count();

            // 模拟分批查询：总记录数50条，每次查询20条，  分三次查询 最后一次查询记录数是10
            Integer totalRowCount = count;
            Integer pageSize = 20000;
            Integer writeCount = totalRowCount % pageSize == 0 ? (totalRowCount / pageSize) : (totalRowCount / pageSize + 1);

            // 注： 此处仅仅为了模拟数据，实用环境不需要将最后一次分开，合成一个即可， 参数为： currentPage = i+1;  pageSize = pageSize
            LogResultDTO logResultDTO = new LogResultDTO();
            LogQueryDTO logQueryDTO = new LogQueryDTO();
            List<LogResultDTO> resultDTOS;
            for (int i = 0; i < writeCount; i++) {
                logQueryDTO.setCurrentPage(i);
                logQueryDTO.setPageSize(pageSize);
                resultDTOS = logService.page(logQueryDTO);

                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                excelWriter.write(resultDTOS, writeSheet);
            }
            System.out.println("导出完成"+System.currentTimeMillis());
            System.out.println("耗时"+(System.currentTimeMillis()-start));
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Test
    public void repeatWrite2(){
        // 方法2 如果写到不同的sheet 同一个对象
        String fileName = "E:\\temp\\" + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = null;
        try {
            // 这里 指定文件
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            for (int i = 0; i < 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();

                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<DemoData> data = data();
                excelWriter.write(data, writeSheet);
            }
        } finally {

            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();

            }

        }
    }
}
