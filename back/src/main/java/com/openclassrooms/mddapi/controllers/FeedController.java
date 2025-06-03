package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ArticleDto>> getUserFeed(@PathVariable Long userId) {
        List<ArticleDto> feedArticles = articleService.getFeedArticlesForUser(userId);
        return ResponseEntity.ok(feedArticles);
    }
}

