package com.auth.yf.repository;

import com.auth.yf.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  public UserEntity findByUserName(String userName);

  public UserEntity findByEmail(String email);

  @Query("select u from UserEntity u where u.email=:email or u.userName=:userName")
  public UserEntity findByUniqueId(@Param("email") String email, @Param("userName") String userName);

  @Query(value = "delete from app_users as u where u.user_name=:userName", nativeQuery = true)
  @Modifying
  public int deleteByUserName(String userName);
}
