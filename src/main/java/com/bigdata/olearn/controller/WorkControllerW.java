package com.bigdata.olearn.controller;

import com.bigdata.olearn.service.WorkServiceW;
import com.bigdata.olearn.util.BaseResponse;
import com.bigdata.olearn.util.ResultCodeEnum;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/work")
public class WorkControllerW {
    @Autowired
    WorkServiceW workServiceW;
    @Autowired
    BaseResponse br;

    //todo：返回岗位目标标签
    //显示领域标签
    @RequestMapping(value="/showLabel")
    public BaseResponse showLabel(){
        List<Record> data=workServiceW.getLabel();
        br.setResult(ResultCodeEnum.SUCCESS);
        br.setData(data);
        return br;
    }
    //todo：显示岗位列表
    //todo：显示岗位需求能力图谱
    //todo：显示详细的岗位列表

}
