package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentDto;
import com.openclassrooms.mddapi.mappers.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Override
    public CommentDto addComment(CommentDto dto) {
        Comment entity = commentMapper.toEntity(dto, userService, articleService);
        //recuperation du user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.getUserByEmail(email);
        //affectation du user au commentaire
        entity.setUser(user);
        Comment saved = commentRepository.save(entity);
        return commentMapper.toDto(saved, userService, articleService);
    }

    @Override
    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        return comment != null ? commentMapper.toDto(comment, userService, articleService) : null;
    }

    @Override
    public List<CommentDto> getCommentsByArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleIdOrderByDateAsc(articleId);
        return commentMapper.toDtoList(comments, userService, articleService);
    }
}
