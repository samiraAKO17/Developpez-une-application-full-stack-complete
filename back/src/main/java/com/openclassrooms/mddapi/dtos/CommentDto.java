package com.openclassrooms.mddapi.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String comment;
    private Long user;
    private Long article;
    private Date date;

}
