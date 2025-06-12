package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private Date date;
    private Long topic;
    private Long user;
}
