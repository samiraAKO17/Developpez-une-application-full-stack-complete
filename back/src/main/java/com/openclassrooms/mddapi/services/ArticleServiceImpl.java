package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.mappers.ArticleMapper;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {


    private final ArticleMapper articleMapper;
    private final TopicService topicService;
    private final UserService userService;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, @Lazy TopicService topicService, ArticleRepository articleRepository, UserService userService) {
        this.articleMapper = articleMapper;
        this.topicService = topicService;
        this.articleRepository = articleRepository;
        this.userService=userService;
    }

    @Override
    public ArticleDto addArticleDtoArticleDto(ArticleDto dto) {
        Article entity = articleMapper.toEntity(dto, topicService, userService);
        //recuperation du user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.getUserByEmail(email);
        //affectation du user a l'article
        entity.setUser(user);
        Article saved = articleRepository.save(entity);
        return articleMapper.toDto(saved, topicService, userService);
    }

    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        return article != null ? articleMapper.toDto(article, topicService, userService) : null;
    }

    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public List<ArticleDto> getFeedArticlesForUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.getUserByEmail(email);

        // Récupérer tous les articles des topics suivis
        List<Article> articles = user.getTopics().stream()
                .filter(Objects::nonNull)
                .flatMap(topic -> topic.getArticles().stream())
                .sorted(Comparator.comparing(Article::getDate).reversed()) // tri du plus récent au plus ancien
                .collect(Collectors.toList());

        // Mapper les articles en ArticleDto
        return articles.stream()
                .map(article -> articleMapper.toDto(article, topicService, userService))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> getAll() {
        return articleRepository.findAll().stream()
                .map(article -> articleMapper.toDto(article, topicService, userService))
                .collect(Collectors.toList());
    }
}
