package com.igshoppingmall.igshoppingmall.user.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserResponseDto {
    private String email;
    private String username;
    private String id;
    private String nickname;

    // 생성자
    public UserResponseDto(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public UserResponseDto() {

    }
}
