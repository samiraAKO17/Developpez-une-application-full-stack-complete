package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.dtos.TopicDto;
import com.openclassrooms.mddapi.models.Topic;

import java.util.List;

public interface CommentService {
    CommentDto addComment (CommentDto dto);
    CommentDto getCommentById(Long id);
    List<CommentDto> getCommentsByArticle(Long articleId);
}
