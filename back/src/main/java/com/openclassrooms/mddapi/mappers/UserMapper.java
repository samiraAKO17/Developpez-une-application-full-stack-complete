package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserDto;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.TopicServiceImpl;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", imports = {Collectors.class})
public abstract class UserMapper {

    @Mapping(target = "topics", expression = "java(getTopicsFromIds(userDto.getTopics(), topicService))")
    public abstract User toEntity(UserDto userDto, @Context TopicServiceImpl topicService);

    @Mapping(target = "topics", expression = "java(getTopicIdsFromTopics(user.getTopics()))")
    public abstract UserDto toDto(User user, @Context TopicServiceImpl topicService);

    protected List<Topic> getTopicsFromIds(List<Long> ids, TopicServiceImpl topicService) {
        if (ids == null) return null;
        return ids.stream()
                .map(topicService::findById)
                .collect(Collectors.toList());
    }

    protected List<Long> getTopicIdsFromTopics(List<Topic> topics) {
        if (topics == null) return null;
        return topics.stream()
                .map(Topic::getId)
                .collect(Collectors.toList());
    }
}
