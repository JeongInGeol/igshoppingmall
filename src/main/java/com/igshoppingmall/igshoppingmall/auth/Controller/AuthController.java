package com.igshoppingmall.igshoppingmall.auth.Controller;

import com.igshoppingmall.igshoppingmall.auth.service.NaverOAuthService;
import com.igshoppingmall.igshoppingmall.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private NaverOAuthService naverOAuthService;

    // 네이버 로그인 처리 메소드 추가
    @GetMapping("/login/oauth2/code/naver")
    public ResponseEntity<UserResponseDto> naverLogin(@RequestParam String code) {
// 네이버 로그인 코드로부터 사용자 정보를 조회하여 회원가입 처리

        String accessToken = naverOAuthService.getAccessToken(code);
        UserResponseDto userResponse = naverOAuthService.getUserInfo(accessToken);

        // 성공 응답 반환
        return ResponseEntity.ok(userResponse);
    }
}
