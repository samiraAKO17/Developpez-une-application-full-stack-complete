package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user-topics")
public class UserTopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<TopicDto>> getUserTopics(@PathVariable Long userId) {
        List<TopicDto> topics = topicService.getTopicsByUserId(userId);
        return ResponseEntity.ok(topics);
    }
}

