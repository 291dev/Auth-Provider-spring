package com.auth.yf.consts;

public enum Role {
  ROLE_USER(Authorities.USER_AUTHORITIES), ROLE_HR(Authorities.HR_AUTHORITIES),
  ROLE_MANAGER(Authorities.MANAGER_AUTHORITIES), ROLE_ADMIN(Authorities.ADMIN_AUTHORITIES),
  ROLE_SUPER(Authorities.SUPER_AUTHORITIES);

  private String[] authorities;

  private Role(String... authorities) {
    this.authorities = authorities;
  }

  public String[] getAuthorities() {
    return authorities;
  }
}
