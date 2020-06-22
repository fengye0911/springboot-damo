package com.bzdgs.demo.mapper;

import com.bzdgs.demo.model.LogResultDTO;
import com.bzdgs.demo.query.LogQueryDTO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: LogMapper.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/22
 */
@Mapper
public interface LogMapper {

    Page<LogResultDTO> page(LogQueryDTO queryDTO);

    Integer count();
}
