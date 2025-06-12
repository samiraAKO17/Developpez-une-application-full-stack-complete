package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.models.Topic;

import java.util.List;

public interface TopicService {
    TopicDto addTopic (TopicDto dto);
    TopicDto getTopicById(Long id);
    Topic findById(Long id);
    List<TopicDto> getTopicsByUserId();
    List<TopicDto> getAllTopics();
    void subscribeUserToTopic(Long topicId, String username);
    void unsubscribeUserFromTopic(Long topicId, String username);

}
