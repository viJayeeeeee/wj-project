package com.evan.wj.service;

import com.evan.wj.dao.ArticleDAO;
import com.evan.wj.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleDAO articleDAO;

    public List<Article> list() {
        return articleDAO.findAll();
    }

    public Article getOne(int id) {
        return articleDAO.getOne(id);
    }

    public int addOrUpdate(Article article) {
        return articleDAO.save(article).getId();
    }

    public void deleteById(int id) {
        articleDAO.deleteById(id);
    }

    public Page list(int page, int size) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return articleDAO.findAll(PageRequest.of(page, size, sort));
    }
}
