package com.pan.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.pan.blog.dao.pojo.SysUser;
import com.pan.blog.service.LoginService;
import com.pan.blog.service.SysUserService;
import com.pan.blog.utils.JWTUtils;
import com.pan.blog.vo.ErrorCode;
import com.pan.blog.vo.Result;
import com.pan.blog.vo.params.LoginParam;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    private static final String slat = "mszlu!@#";

    /**
     * 登录功能
     * @param loginParam
     * @return     */
    @Override
    public Result login(LoginParam loginParam) {
        //检查参数合法?
        //根据账号密码 查询表
        //如果不存在 失败    如果存在 用JWT生成Token 返回前端
        //Token放入Redis,redis设置token:user信息,设置过期时间
        //认证的时候,先检查token字符串,再去Redis检查对应的user信息
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){//有任何一个空,都属于参数错误
             return  Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password+slat);
        SysUser sysUser = sysUserService.findUser(account,password);
        if(sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return  Result.success(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    /**
     * 注册功能
     * @param loginParam
     * @return
     */
    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.验证参数是否合法
         * 2.判断账户是否存在 若存在,提示已注册
         * 3.不存在,注册用户,生成Token,传入Redis
         * 以上任意一个步骤出现问题,都会回滚(注册失败)
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if(StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)) {
            return  Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = this.sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);
        //注册 生成Token
        String token  =JWTUtils.createToken(sysUser.getId());
        //放入Redis
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return  null;
        }
        Map<String,Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap == null){
            return  null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_"+token);
        if (StringUtils.isBlank(userJson)){
            return  null;
        }
        SysUser sysUser = JSON.parseObject(userJson,SysUser.class);
        return  sysUser;


    }
}