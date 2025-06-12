package com.openclassrooms.mddapi.dtos;

import com.openclassrooms.mddapi.models.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class TopicDto {
    private Long id;
    private String title;
    private String description;
    private List<Long> articles;
}
