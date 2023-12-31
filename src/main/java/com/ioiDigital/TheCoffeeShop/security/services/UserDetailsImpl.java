package com.ioiDigital.TheCoffeeShop.security.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.ioiDigital.TheCoffeeShop.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  @JsonIgnore
  private String password;

  private SimpleGrantedAuthority authority;

  public UserDetailsImpl(Long id, String username, String password,
                         SimpleGrantedAuthority authority) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authority = authority;
  }

  public static UserDetailsImpl build(User user) {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

    return new UserDetailsImpl(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            authority);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(authority);
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
