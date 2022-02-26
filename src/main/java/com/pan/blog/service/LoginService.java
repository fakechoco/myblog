package com.pan.blog.service;

import com.pan.blog.dao.pojo.SysUser;
import com.pan.blog.vo.Result;
import com.pan.blog.vo.params.LoginParam;



public interface LoginService {
    //登录
    Result login(LoginParam loginParam);

    Result logout(String token);

    Result register(LoginParam loginParam);

    SysUser checkToken(String token);
}
