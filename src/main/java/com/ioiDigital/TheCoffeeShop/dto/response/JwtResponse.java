package com.ioiDigital.TheCoffeeShop.dto.response;

import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String username;
  private String role;

  public JwtResponse(String accessToken, Long id, String username, String role) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.role = role;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }
}
