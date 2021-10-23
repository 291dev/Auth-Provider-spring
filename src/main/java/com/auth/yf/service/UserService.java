package com.auth.yf.service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

import com.auth.yf.consts.Authorities;
import com.auth.yf.consts.Messages;
import com.auth.yf.consts.Role;
import com.auth.yf.domain.UserPrincipal;
import com.auth.yf.domain.UserRegisterReqBean;
import com.auth.yf.domain.exception.UserAlreadyExistException;
import com.auth.yf.entity.UserEntity;
import com.auth.yf.repository.UserRepository;
import com.auth.yf.util.DateTimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private EmailService emailService;

  @Value("${validconfsec}")
  private Long validconfsec;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    log.info("UserService#loadUserByUsername called.");
    UserEntity user = userRepository.findByEmail(email);
    if (user == null) {
      log.warn(Messages.USER_NOT_FOUND.getMessage(), email);
      throw new UsernameNotFoundException(Messages.USER_NOT_FOUND.getMessageWithFormat(email));
    } else {
      validateLoginAttempt(user);
      user.setLastLoginDate(DateTimeUtil.toOffsetJstDateTimeFromDate(new Date()));
      userRepository.save(user);
      UserPrincipal userPrincipal = new UserPrincipal(user);
      log.info("Sign in: {}", email);
      return userPrincipal;
    }
  }

  public String register(UserRegisterReqBean user) throws Exception {
    // userNameはuuidとする
    String userName = UUID.randomUUID().toString();
    validateNewUser(user);
    UserEntity newUser = new UserEntity();
    newUser.setUserName(userName);
    newUser.setFirstName(user.getFirstName());
    newUser.setLastName(user.getLastName());
    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    newUser.setEmail(user.getEmail());
    newUser.setTelephoneNumber(user.getTelephoneNumber());
    newUser.setBirthOfDate(user.getDateOfBirth());
    newUser.setJoinDate(DateTimeUtil.toOffsetJstDateTimeFromDate());
    // 本人確認が済んでいない
    newUser.setActive(false);
    newUser.setNotLocked(true);
    newUser.setRoles(Role.ROLE_USER.name());
    newUser.setAuthorities(Authorities.USER_AUTHORITIES);
    emailService.sendNewPasswordEmail(user.getFirstName(), user.getEmail(), userName);
    userRepository.save(newUser);
    return userName;
  }

  public boolean configure(String uuid) throws Exception {
    UserEntity user = userRepository.findByUserName(uuid);
    if (user == null) {
      throw new Exception();
    }
    var joinDate = user.getJoinDate().toEpochSecond();
    var now = OffsetDateTime.now().toEpochSecond();
    if (now - joinDate > validconfsec) {
      throw new Exception("");
    }
    user.setActive(true);
    userRepository.save(user);
    return true;
  }

  private void validateNewUser(UserRegisterReqBean user) throws Exception {
    UserEntity userEntityByNewEmail = userRepository.findByEmail(user.getEmail());
    if (userEntityByNewEmail != null) {
      throw new UserAlreadyExistException(Messages.USER_ALREADY_EXISTS.getMessage());
    }
  }

  private void validateLoginAttempt(UserEntity user) {
    // TODO Impl
  }

  public UserEntity findByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }

  public UserEntity findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public boolean deleteUser(String userName) {
    userRepository.deleteByUserName(userName);
    return true;
  }
}
