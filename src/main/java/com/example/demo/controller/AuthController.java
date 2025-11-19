package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AuthRequest;
import com.example.demo.entity.User;
import com.example.demo.service.AuthService;

@RestController // 1. "이건 API 컨트롤러입니다"
@RequestMapping("/auth") // 2. 이 컨트롤러의 모든 API는 '/auth'라는 주소로 시작합니다.
public class AuthController {

    private final AuthService authService; // 3. Service를 주입받음

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ========== 회원가입 API (/auth/signup) ==========
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthRequest request) {
        try {
            authService.signup(request.getUsername(), request.getPassword(), request.getEmail());
            return ResponseEntity.ok("회원가입 성공!"); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        }
    }

    // ========== 로그인 API (/auth/login) ==========
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        try {
            User user = authService.login(request.getUsername(), request.getPassword());
            
            // (나중에 JWT 토큰을 생성해서 반환하는 로직)
            String token = "임시_로그인_성공_토큰_for_" + user.getUsername(); 

            return ResponseEntity.ok(token); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        }
    }
}