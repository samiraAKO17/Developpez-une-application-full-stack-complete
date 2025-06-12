package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("api/topics")
@RestController
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TopicDto>> getAll() {

            return ResponseEntity.ok(topicService.getAllTopics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createTopic(@RequestBody TopicDto topicDTO) {
        try {
            TopicDto topic = topicService.addTopic(topicDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(topic);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la création du topic : " + e.getMessage());
        }
    }

    @PostMapping("/{topicId}/subscribe")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> subscribeToTopic(@PathVariable Long topicId, Principal principal) {
        try {
            topicService.subscribeUserToTopic(topicId, principal.getName());
            return ResponseEntity.ok("Abonnement réussi");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur abonnement : " + e.getMessage());
        }
    }

    @DeleteMapping("/{topicId}/unsubscribe")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> unsubscribeFromTopic(@PathVariable Long topicId, Principal principal) {
        try {
            topicService.unsubscribeUserFromTopic(topicId, principal.getName());
            return ResponseEntity.ok("Désabonnement réussi");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur désabonnement : " + e.getMessage());
        }
    }

}
