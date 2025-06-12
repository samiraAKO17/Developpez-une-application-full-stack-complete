package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.*;
import com.openclassrooms.mddapi.services.UserServiceImpl;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class ArticleMapper {

    @Mapping(target = "topic", source = "topic", qualifiedByName = "mapTopic")
    @Mapping(target = "user", source = "user", qualifiedByName = "mapUser")
    public abstract Article toEntity(ArticleDto dto, @Context TopicService topicService, @Context UserService userService);

    @Mapping(target = "topic", source = "topic.id")
    @Mapping(target = "user", source = "user.id")
    public abstract ArticleDto toDto(Article entity, @Context TopicService topicService, @Context UserService userService);

    @Named("mapTopic")
    protected Topic mapTopic(Long topicId, @Context TopicService topicService) {
        return topicId != null ? topicService.findById(topicId) : null;
    }

    @Named("mapUser")
    protected User mapUser(Long userId, @Context UserService userService) {
        return userId != null ? userService.findById(userId) : null;
    }
}
