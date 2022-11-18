package com.kdatalab.scheduler.job.service;

import com.kdatalab.scheduler.job.mapper.SchedulerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final SchedulerMapper schedulerMapper;

    @Override
    public boolean insertAddLog(Map<String, Object> params) throws Exception {

        schedulerMapper.insertAddLog(params);

        return true;
    }

    @Override
    public List<Map<String, Object>> getLogDataList() throws Exception {
        return schedulerMapper.getLogDataList();
    }

    @Override
    public boolean insert(Map<String, Object> params) throws Exception {
        return false;
    }

    @Override
    public boolean insertLog(Map<String, Object> params) throws Exception {

        schedulerMapper.insertLog(params);

        return true;
    }
}
