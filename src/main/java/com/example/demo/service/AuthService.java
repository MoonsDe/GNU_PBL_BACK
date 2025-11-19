package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 1단계에서 등록한 암호화 도구

    // '생성자 주입' (Spring이 알아서 UserRepository와 PasswordEncoder를 넣어줌)
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ========== 회원가입 로직 ==========
    public User signup(String username, String password, String email) {
        // 1. 중복 사용자 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("이미 사용 중인 아이디입니다."); // (나중에 더 좋은 예외처리로 변경)
        }

        // 2. 사용자 생성 및 비밀번호 암호화
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // ◀◀◀ 비밀번호 암호화!

        // 3. DB에 저장
        return userRepository.save(user);
    }

    // ========== 로그인 로직 ==========
    public User login(String username, String password) {
        // 1. 아이디로 사용자 찾기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 2. 비밀번호 확인
        // (입력된 비밀번호, DB에 저장된 암호화된 비밀번호) 비교
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 인증 성공 (나중에는 'JWT 토큰' 등을 반환하게 됩니다)
        return user;
    }
}