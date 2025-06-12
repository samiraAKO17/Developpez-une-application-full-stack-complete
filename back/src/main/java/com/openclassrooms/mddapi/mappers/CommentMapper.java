package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ArticleServiceImpl;
import com.openclassrooms.mddapi.services.UserServiceImpl;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public abstract class CommentMapper {

    @Mapping(target = "user", source = "user", qualifiedByName = "mapUser")
    @Mapping(target = "article", source = "article", qualifiedByName = "mapArticle")
    public abstract Comment toEntity(CommentDto dto, @Context UserServiceImpl userService, @Context ArticleServiceImpl articleService);

    @Mapping(target = "user", source = "user.id")
    @Mapping(target = "article", source = "article.id")
    public abstract CommentDto toDto(Comment entity, @Context UserServiceImpl userService, @Context ArticleServiceImpl articleService);

    @Named("mapUser")
    protected User mapUser(Long userId, @Context UserServiceImpl userService) {
        return userId != null ? userService.findById(userId) : null;
    }

    @Named("mapArticle")
    protected Article mapArticle(Long articleId, @Context ArticleServiceImpl articleService) {
        return articleId != null ? articleService.findById(articleId) : null;
    }

    public List<CommentDto> toDtoList(List<Comment> comments, @Context UserServiceImpl userService, @Context ArticleServiceImpl articleService) {
        return comments.stream()
                .map(comment -> toDto(comment, userService, articleService))
                .collect(Collectors.toList());
    }
}
