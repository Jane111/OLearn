package com.bigdata.olearn.controller;

import com.bigdata.olearn.service.UserServiceL;
import com.bigdata.olearn.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserControllerL {
    @Autowired
    UserServiceL userServiceL;
    @Autowired
    BaseResponse br;
    //todo：将知识点加入学习路线
    //todo：将课程加入学习路线
    //todo：显示我的课程图谱
    //todo：进入详细知识点查看知识点的学习情况
    //todo：显示课程目录
    //todo：点亮课程进度
    //todo：进入详细知识点查看已经学习过的课程
    //todo：显示个人能力标签
    //todo：注册
    //todo：登录
}
