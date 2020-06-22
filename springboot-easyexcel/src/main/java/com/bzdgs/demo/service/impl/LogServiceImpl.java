package com.bzdgs.demo.service.impl;

import com.bzdgs.demo.mapper.LogMapper;
import com.bzdgs.demo.model.LogResultDTO;
import com.bzdgs.demo.query.LogQueryDTO;
import com.bzdgs.demo.service.ILogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: LogServiceImpl.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/22
 */
@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    private LogMapper logMapper;
    @Override
    public List page(LogQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getCurrentPage(), queryDTO.getPageSize());
        Page<LogResultDTO> page = logMapper.page(queryDTO);
        List<LogResultDTO> result = page.getResult();
        return result;
    }

    @Override
    public Integer count() {
        return logMapper.count();
    }
}
