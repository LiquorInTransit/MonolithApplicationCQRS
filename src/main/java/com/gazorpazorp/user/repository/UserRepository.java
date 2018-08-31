package com.gazorpazorp.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gazorpazorp.user.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
	UserEntity findByEmail(@Param("email") String email);
}
