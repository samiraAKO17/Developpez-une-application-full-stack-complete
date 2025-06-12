package com.openclassrooms.mddapi.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String name;

  public JwtResponse(String accessToken, Long id, String username,String firstName) {
    this.token = accessToken;
    this.id = id;
    this.name = firstName;
    this.username = username;
  }
}
