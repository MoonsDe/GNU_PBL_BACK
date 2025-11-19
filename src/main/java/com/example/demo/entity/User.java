package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter; // ◀◀◀ 1. import 구문이 있는지 확인
import lombok.Setter; // ◀◀◀ 2. import 구문이 있는지 확인

@Entity
@Table(name = "users")
@Getter // ◀◀◀ 3. 이게 있어야 getPassword()가 자동으로 생깁니다
@Setter // ◀◀◀ 4. 이게 있어야 setPassword()가 자동으로 생깁니다
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    
    private String email;
    
    private String password; // (이건 이전에 추가하셨죠)
    
    // @Getter와 @Setter가 자동으로
    // getPassword(), setPassword(), getUsername(), setUsername() 등을
    // 모두 만들어 줍니다.
}