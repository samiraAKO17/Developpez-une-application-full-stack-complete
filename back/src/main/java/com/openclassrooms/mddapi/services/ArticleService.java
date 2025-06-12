package com.openclassrooms.mddapi.services;


import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.models.Article;

import java.util.List;

public interface ArticleService {
    ArticleDto addArticleDtoArticleDto(ArticleDto dto);
    ArticleDto getArticleById(Long id);
    Article findById(Long id);
    List<ArticleDto> getFeedArticlesForUser();
    List<ArticleDto> getAll();


}
