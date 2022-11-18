package com.kdatalab.scheduler.job.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SchedulerMapper {
    boolean insertLog (Map<String, Object> params) throws Exception;
    boolean insertAddLog (Map<String, Object> params) throws Exception;
    List<Map<String, Object>> getLogDataList() throws Exception;
}
