package com.openclassrooms.mddapi.dtos;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class TopicDtos {
    private Long id;
    private String title;
    private String description;


}
