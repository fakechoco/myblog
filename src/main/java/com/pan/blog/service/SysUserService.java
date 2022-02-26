package com.pan.blog.service;

import com.pan.blog.dao.pojo.SysUser;
import com.pan.blog.vo.Result;
import com.pan.blog.vo.UserVo;

public interface SysUserService {

   Result getUserInfoByToken(String token) ;
    //根据token查询用户信息
    UserVo findUserVoById(Long id);
    //根据用户id寻找,返回UserVo
    SysUser findUserById(Long id);
    //根据用户ID查找
    SysUser findUser(String account, String password);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);
    //根据账号密码查找
}
