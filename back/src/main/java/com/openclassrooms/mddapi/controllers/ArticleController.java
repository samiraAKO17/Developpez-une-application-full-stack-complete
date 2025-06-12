package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ArticleDto;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("api/articles")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createArticle(@RequestBody ArticleDto articleDTO, Principal principal) {
        try {

            return ResponseEntity.status(HttpStatus.CREATED).body(articleService.addArticleDtoArticleDto(articleDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur création article : " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @GetMapping("/comments/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCommentsByArticle(@PathVariable Long articleId) {
        try {
            return ResponseEntity.ok(commentService.getCommentsByArticle(articleId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur récupération commentaires : " + e.getMessage());
        }
    }
}
