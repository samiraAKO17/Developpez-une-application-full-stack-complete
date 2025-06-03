package com.openclassrooms.mddapi.dtos;

import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENTS")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    private Long id;
    private String comment;
    private User user;
    private Article article;
    private Date date;

}
