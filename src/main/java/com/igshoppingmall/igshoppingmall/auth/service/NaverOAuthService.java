package com.igshoppingmall.igshoppingmall.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igshoppingmall.igshoppingmall.user.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NaverOAuthService {

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.redirect.uri}")
    private String naverRedirectUri;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public NaverOAuthService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // 1. 네이버에서 Access Token을 받아오는 메소드
    public String getAccessToken(String code) {
        String tokenUrl = UriComponentsBuilder.fromHttpUrl("https://nid.naver.com/oauth2.0/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", naverClientId)
                .queryParam("client_secret", naverClientSecret)
                .queryParam("redirect_uri", naverRedirectUri)
                .queryParam("code", code)
                .queryParam("state", "STATE_STRING")
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(tokenUrl, String.class);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return root.path("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get access token from Naver", e);
        }
    }

    // 2. Access Token으로 사용자 정보를 가져오는 메소드
    public UserResponseDto getUserInfo(String accessToken) {
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode responseNode = root.path("response");

            // 사용자 정보를 UserResponseDto로 변환하여 반환
            UserResponseDto user = new UserResponseDto();
            user.setId(responseNode.path("id").asText());
            user.setNickname(responseNode.path("nickname").asText());
            user.setEmail(responseNode.path("email").asText());

            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user info from Naver", e);
        }
    }
}
