package com.igshoppingmall.igshoppingmall.dto;

public class UserResponseDto {
    private String email;
    private String username;

    // 생성자
    public UserResponseDto(String email, String username) {
        this.email = email;
        this.username = username;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
