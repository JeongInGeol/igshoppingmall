package com.igshoppingmall.igshoppingmall.service;

import com.igshoppingmall.igshoppingmall.model.User;
import com.igshoppingmall.igshoppingmall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 저장
    public User saveUser(User user) {
        // 비밀번호 해시
        user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // 이메일로 사용자 찾기
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 사용자 존재 여부 확인
    public boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
