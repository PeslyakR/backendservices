package com.services.app.services.support;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthUser extends User {

  public AuthUser(String username, String password,
      Collection<? extends GrantedAuthority> authorities,
      Long empId,
      String employeeName) {
    super(username, password, authorities);
    this.empId = empId;
    this.employeeName = employeeName;
  }

  private Long empId;
  private String employeeName;

}
