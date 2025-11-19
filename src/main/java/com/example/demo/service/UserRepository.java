package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

import java.util.Optional;

// JpaRepository<[어떤 Entity?], [ID 필드 타입?]>
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Spring Data JPA가 '메소드 이름'을 보고 SQL을 자동으로 만들어줍니다.
    // "username을 기준으로 User를 찾아줘"
    Optional<User> findByUsername(String username);
}