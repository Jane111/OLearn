package com.bigdata.olearn.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceW {
    public List<Record> getLabel(){
        return Db.find("SELECT id,classname FROM work_cluster");
    }
}
