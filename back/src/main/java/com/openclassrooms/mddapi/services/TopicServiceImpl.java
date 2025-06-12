package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.mappers.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicMapper topicMapper;
    private final ArticleService articleService;
    private final TopicRepository topicRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public TopicServiceImpl(TopicMapper topicMapper, @Lazy ArticleService articleService, TopicRepository topicRepository, UserService userService,@Lazy UserRepository userRepository) {
        this.topicMapper = topicMapper;
        this.articleService = articleService;
        this.topicRepository = topicRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public TopicDto addTopic(TopicDto dto) {
        Topic topic = topicMapper.toEntity(dto, articleService);
        Topic savedTopic = topicRepository.save(topic);
        return topicMapper.toDto(savedTopic, articleService);
    }

    @Override
    public TopicDto getTopicById(Long id) {
        Optional<Topic> topicOpt = topicRepository.findById(id);
        if (topicOpt.isPresent()) {
            return topicMapper.toDto(topicOpt.get(), articleService);
        }
        // gérer le cas où le topic n'existe pas (exception ou null)
        return null;
    }

    @Override
    public Topic findById(Long id) {
        return topicRepository.findById(id).orElse(null);
    }

    @Override
    public List<TopicDto> getTopicsByUserId() {
        //recuperation du user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.getUserByEmail(email);

        return user.getTopics().stream()
                .filter(Objects::nonNull)
                .map(topic -> topicMapper.toDto(topic, articleService))
                .collect(Collectors.toList());
    }

    @Override
    public List<TopicDto> getAllTopics() {
        return topicRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(topic -> topicMapper.toDto(topic, articleService))
                .collect(Collectors.toList());
    }

    @Override
    public void subscribeUserToTopic(Long topicId, String username) {
        User user = userService.getUserByEmail(username);
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic non trouvé"));

        user.getTopics().add(topic);
        userRepository.save(user);
    }

    @Override
    public void unsubscribeUserFromTopic(Long topicId, String username) {
        User user = userService.getUserByEmail(username);

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic non trouvé"));

        user.getTopics().remove(topic);
        userRepository.save(user);
    }

}