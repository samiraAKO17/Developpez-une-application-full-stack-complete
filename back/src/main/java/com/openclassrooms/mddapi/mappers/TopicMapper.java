package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.ArticleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Context;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class TopicMapper {

    @Mappings({
            @Mapping(target = "articles", expression = "java(mapArticleIdsToArticles(topicDto.getArticles(), articleService))")
    })
    public abstract Topic toEntity(TopicDto topicDto, @Context ArticleService articleService);

    @Mappings({
            @Mapping(target = "articles", expression = "java(mapArticlesToArticleIds(topic.getArticles()))")
    })
    public abstract TopicDto toDto(Topic topic, @Context ArticleService articleService);

    protected List<Article> mapArticleIdsToArticles(List<Long> ids, @Context ArticleService articleService) {
        if (ids == null) return null;
        return ids.stream()
                .map(articleService::findById)
                .collect(Collectors.toList());
    }

    protected List<Long> mapArticlesToArticleIds(List<Article> articles) {
        if (articles == null) return null;
        return articles.stream()
                .map(Article::getId)
                .collect(Collectors.toList());
    }
}
