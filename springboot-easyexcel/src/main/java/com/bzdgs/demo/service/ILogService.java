package com.bzdgs.demo.service;

import com.bzdgs.demo.model.LogResultDTO;
import com.bzdgs.demo.query.LogQueryDTO;

import java.util.List;

public interface ILogService {

    List<LogResultDTO> page(LogQueryDTO queryDTO);

    Integer count();
}
