package com.auth.yf.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.auth.yf.entity.UserEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrincipal implements UserDetails {

  private UserEntity userEntity;

  public UserPrincipal(UserEntity user) {
    this.userEntity = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.stream(this.userEntity.getAuthorities()).map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return this.userEntity.getPassword();
  }

  @Override
  public String getUsername() {
    return this.userEntity.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.userEntity.isNotLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.userEntity.isActive();
  }

}
