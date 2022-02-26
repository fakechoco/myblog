package com.pan.blog.controller;

import com.pan.blog.service.ArticleService;
import com.pan.blog.vo.ArticleVo;
import com.pan.blog.vo.Result;
import com.pan.blog.vo.params.ArticleParam;
import com.pan.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Value("${article.max}")
    private  int limit;
    /*
        首页文章列表
        @param pageParams
        @return Result
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);
    }

    /**
     * 首页 最热文章
     * @return
     */
    @PostMapping("hot")
    public  Result hotArticle(){
        return articleService.hotArticle(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("new")
    public  Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    @PostMapping("view/{id}")
    public  Result findArticleById (@PathVariable("id") Long articleId){

        ArticleVo articleVo = articleService.findArticleById(articleId);

        return Result.success(articleVo);
    }
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
