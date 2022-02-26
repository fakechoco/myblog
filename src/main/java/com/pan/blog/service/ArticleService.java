package com.pan.blog.service;

import com.pan.blog.vo.ArticleBodyVo;
import com.pan.blog.vo.ArticleVo;
import com.pan.blog.vo.Result;
import com.pan.blog.vo.params.ArticleParam;
import com.pan.blog.vo.params.PageParams;

public interface ArticleService {

    /**
     *  分页查询
     * @param pageParams
     * @return
     */
    Result listArticle (PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 首页,文章归档
     * @return
     */
    Result listArchives();

    ArticleVo findArticleById(Long articleId);

    Result publish(ArticleParam articleParam);
}
