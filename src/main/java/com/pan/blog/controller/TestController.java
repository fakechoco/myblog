package com.pan.blog.controller;

import com.pan.blog.dao.pojo.SysUser;
import com.pan.blog.utils.UserThreadLocal;
import com.pan.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {



    @RequestMapping
    public Result test(){

        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}