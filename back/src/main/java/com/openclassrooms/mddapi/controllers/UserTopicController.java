package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user-topics")
public class UserTopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TopicDto>> getUserTopics() {
        List<TopicDto> topics = topicService.getTopicsByUserId();
        return ResponseEntity.ok(topics);
    }
}

