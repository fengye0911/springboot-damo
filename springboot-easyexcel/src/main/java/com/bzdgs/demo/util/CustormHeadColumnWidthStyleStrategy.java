package com.bzdgs.demo.util;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.style.column.AbstractHeadColumnWidthStyleStrategy;

/**
 * ClassName: CustormHeadColumnWidthStyleStrategy.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/22
 */
public class CustormHeadColumnWidthStyleStrategy extends AbstractHeadColumnWidthStyleStrategy {
    @Override
    protected Integer columnWidth(Head head, Integer columnIndex) {
        return null;
    }
}
