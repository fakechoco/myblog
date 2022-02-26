package com.pan.blog.service;

import com.pan.blog.vo.Result;
import com.pan.blog.vo.params.CommentParam;

public interface CommentService {

//根据文章id查找评论
    Result commentsByArticleId(Long articleId);

    //
    Result comment(CommentParam commentParam);

}
