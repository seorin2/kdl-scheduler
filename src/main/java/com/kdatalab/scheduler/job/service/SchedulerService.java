package com.kdatalab.scheduler.job.service;

import java.util.List;
import java.util.Map;

public interface SchedulerService {

    boolean insert(Map<String, Object> params) throws Exception;
    boolean insertLog(Map<String, Object> params) throws Exception;
    boolean insertAddLog(Map<String, Object> params) throws Exception;
    List<Map<String, Object>> getLogDataList() throws Exception;
}
