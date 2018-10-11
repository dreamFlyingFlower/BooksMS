package com.wy.service;

import com.wy.dao.RecordDao;
import com.wy.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("recordService")
public class RecordService extends BaseService<Record> {
    @Autowired
    private RecordDao recordDao;

    @Override
    public RecordDao getDao() {
        return recordDao;
    }
}