package com.bzdgs.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: ExcelData.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/22
 */
@Data
public class ExcelData implements Serializable {
    private static final long serialVersionUID = 8683288107021034861L;
    private List<String> titles;
    private List<List<Object>> rows;
    private String name;
    private float heightInPoints;

}

