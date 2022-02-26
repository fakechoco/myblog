package com.pan.blog.controller;

import com.pan.blog.utils.QiniuUtils;
import com.pan.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file){
        //file.getOriginalFilename() 获取原始文件名称 比如aa.png
        //UUID.randomUUID().toString() 随机生成一个唯一的名称
        //StringUtils.substringAfterLast(file.getOriginalFilename(), ".")  以" . "分割原始名称
        //并且取后面一段(也就是文件后缀)
        //最后与生成的随机名称拼接,获得新的唯一文件名

        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            return Result.success(QiniuUtils.url +"/"+ fileName);
        }
        return Result.fail(20001,"上传失败");
    }
}