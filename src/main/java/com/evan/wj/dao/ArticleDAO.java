package com.evan.wj.dao;

import com.evan.wj.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDAO extends JpaRepository<Article, Integer> {
}
