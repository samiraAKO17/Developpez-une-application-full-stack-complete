package com.openclassrooms.mddapi.dtos;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLES")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Article {
    private Long id;
    private String title;
    private String content;
    private Date date;
    private Topic topic;
    private User user;


}
