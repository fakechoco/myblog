package com.pan.blog.dao.pojo;

import lombok.Data;

/**
 * 文章内容表
 */
@Data
public class ArticleBody {

    private Long id;
    private String content;  //文章内容,MD格式
    private String contentHtml; // HTML格式
    private Long articleId;
}