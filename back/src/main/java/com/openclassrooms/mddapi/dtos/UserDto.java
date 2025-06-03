package com.openclassrooms.mddapi.dtos;

import com.openclassrooms.mddapi.models.Topic;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Topic> topic;

}
