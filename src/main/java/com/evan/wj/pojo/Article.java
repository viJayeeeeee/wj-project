package com.evan.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "jotter_article")
@Setter
@Getter
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    String articleTitle;
    String articleContentHtml;
    String articleContentMd;
    String articleAbstract;
    String articleCover;
    String articleDate;
}
