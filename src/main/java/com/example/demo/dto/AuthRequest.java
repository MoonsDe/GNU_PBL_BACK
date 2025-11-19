package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

// API 요청(Request)을 받을 때 사용할 '그릇'
@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;
    private String email; // (로그인 시에는 null일 수 있음)
}