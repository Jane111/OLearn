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
}
