package com.bigdata.olearn.controller;

import com.bigdata.olearn.model.User;
import com.bigdata.olearn.service.UserServiceW;
import com.bigdata.olearn.util.BaseResponse;
import com.bigdata.olearn.util.ResultCodeEnum;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControllerW {
    @Autowired
    UserServiceW userServiceW;
    //将知识点加入学习路线
    @RequestMapping(value="/addKnowledgeToPath")
    public BaseResponse addKnowledgeToPath(
            @RequestParam(value = "userId")BigInteger userId,
            @RequestParam(value = "clusterId")BigInteger clusterId
    ){
        BaseResponse br=new BaseResponse();
        userServiceW.addKnowledgeLabel(userId,clusterId);
        br.setResult(ResultCodeEnum.SUCCESS);
        return  br;
    }
    //todo:将课程加入学习路线(未测试)
    @RequestMapping(value="/addCourseToPath")
    public BaseResponse addCourseToPath(
            @RequestParam(value = "userId")BigInteger userId,
            @RequestParam(value = "moocId[]")BigInteger[] moocId,
            @RequestParam(value = "clusterId")BigInteger clusterId,
            @RequestParam(value = "theMinRank")Integer theMinRank
    ){
        userServiceW.addCourseToPath(userId,moocId,clusterId,theMinRank);
        BaseResponse br=new BaseResponse();
        br.setResult(ResultCodeEnum.SUCCESS);
        return br;
    }
    //todo：显示我的课程图谱
    //进入详细知识点查看知识点的学习情况
    @RequestMapping(value="/showKnowledgeLearnSituaton")
    public BaseResponse showKnowledgeLearnSituaton(
            @RequestParam(value = "userId") BigInteger userId,
            @RequestParam(value = "clusterId") BigInteger clusterId
    ){
        List<Record> data= userServiceW.showKnowledgeLearnSituaton(userId,clusterId);
        BaseResponse br=new BaseResponse();
        br.setResult(ResultCodeEnum.SUCCESS);
        br.setData(data);
        return br;
    }
    //显示用户学习的课程目录
    @RequestMapping(value="/showMySchedule")
    public BaseResponse showMySchedule(
            @RequestParam(value = "userId") BigInteger userId,
            @RequestParam(value = "clusterId") BigInteger clusterId,
            @RequestParam(value = "rank") Integer rank
    ){
        List<Record> data= userServiceW.showMySchedule(userId,clusterId,rank);
        BaseResponse br=new BaseResponse();
        br.setResult(ResultCodeEnum.SUCCESS);
        br.setData(data);
        return br;
    }
    //点亮课程进度
    @RequestMapping(value="/setMyMoocPlan")
    public BaseResponse setMyMoocPlan(
            @RequestParam(value = "userId") BigInteger userId,
            @RequestParam(value = "moocId") BigInteger moocId,
            @RequestParam(value = "sequence") Integer sequence
    ){
        userServiceW.setMyMoocPlan(userId,moocId,sequence);
        BaseResponse br=new BaseResponse();
        br.setResult(ResultCodeEnum.SUCCESS);
        return br;
    }
    //todo：进入详细知识点查看已经学习过的课程

    //显示个人能力标签
    @RequestMapping(value="/showMyLabel")
    public BaseResponse showMyLabel(
            @RequestParam(value = "userId") BigInteger userId
    ){
        List<Record> data = userServiceW.showMyLabel(userId);
        BaseResponse br=new BaseResponse();
        br.setData(data);
        br.setResult(ResultCodeEnum.SUCCESS);
        return br;
    }
    //todo：注册
    @RequestMapping(value="/register")
    public BaseResponse register(
            @RequestParam(value = "nickname") String nickname,
            @RequestParam(value = "mail") String mail,
            @RequestParam(value = "password") String password
    ){
        ResultCodeEnum result = userServiceW.register(nickname,mail,password);
        BaseResponse br=new BaseResponse();
        br.setResult(result);
        return br;
    }
    //todo：登录
    @RequestMapping(value="/login")
    public BaseResponse login(
            @RequestParam(value = "mail") String mail,
            @RequestParam(value = "password") String password
    ){
        User user = userServiceW.login(mail,password);
        BaseResponse br=new BaseResponse();
        if(user==null){
            br.setResult(ResultCodeEnum.ERROR_ACCOUNT_OR_PASSWORD);
        }else {
            br.setData(user);
            br.setResult(ResultCodeEnum.SUCCESS);
        }
        return br;
    }
}
