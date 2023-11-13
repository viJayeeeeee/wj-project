package com.evan.wj.controller;

import com.evan.wj.pojo.Article;
import com.evan.wj.result.Result;
import com.evan.wj.result.ResultFactory;
import com.evan.wj.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("/api/article")
    public List<Article> list() {
        return articleService.list();
    }

    @GetMapping("/api/article/{id}")
    public Article getOneArticle(@PathVariable("id") int id) {
        return articleService.getOne(id);
    }

    //    @PutMapping("/api/admin/content/article")
    @PostMapping("/api/admin/content/article")
    public Result saveArticle(@RequestBody Article article) {
        if (article.getArticleContentHtml().isEmpty()) {
            return ResultFactory.buildFailResult("文章正文为空！");
        }
        int id = -1;
        try {
            // 标题和摘要为空则补足
            if (article.getArticleTitle().isEmpty()) {
                System.out.println("[标题]补足");
                String title = article.getArticleContentMd().trim();
                article.setArticleTitle(title.substring(0, Math.min(title.length(), 5)));
            }
            if (article.getArticleAbstract().isEmpty()) {
                System.out.println("[摘要]补足");
                String contentAbstract = article.getArticleContentMd().trim();
                article.setArticleAbstract(contentAbstract.substring(0, Math.min(contentAbstract.length(), 20)));
            }
            id = articleService.addOrUpdate(article);
        } catch (Exception e) {
            return ResultFactory.buildFailResult(e.toString());
        }
        return ResultFactory.buildSuccessResult(id);
    }

    @DeleteMapping("/api/admin/content/article/{id}")
    public Result deleteArticle(@PathVariable("id") int id) {
        try {
            articleService.deleteById(id);
        } catch (Exception e) {
            return ResultFactory.buildFailResult(e.toString());
        }
        return ResultFactory.buildSuccessResult("删除成功！");
    }

    @GetMapping("/api/article/{size}/{page}")
    public Page listArticles(@PathVariable("size") int size, @PathVariable("page") int page) {
        return articleService.list(page - 1, size);
    }

}
