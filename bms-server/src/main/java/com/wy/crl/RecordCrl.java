package com.wy.crl;

import com.wy.entity.Record;
import com.wy.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("record")
public class RecordCrl extends BaseCrl<Record> {
    @Autowired
    private RecordService recordService;

    @Override
    public RecordService getService() {
        return recordService;
    }
}