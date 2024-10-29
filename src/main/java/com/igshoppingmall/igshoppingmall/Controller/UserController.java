package com.igshoppingmall.igshoppingmall.Controller;

import ch.qos.logback.classic.Logger;
import com.igshoppingmall.igshoppingmall.dto.UserResponseDto;
import com.igshoppingmall.igshoppingmall.model.User;
import com.igshoppingmall.igshoppingmall.service.UserService;
import com.igshoppingmall.igshoppingmall.utils.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;



    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        if (userService.userExists(user.getEmail())) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    // 이메일로 사용자 조회
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // 로그인 로직 처리
        User findUser = userService.findByEmail(user.getEmail());
        Boolean isAuthenticated = false;

        logger.info("User 정보 : {}", user.toString());

        if (findUser != null) {
            // 비밀번호 비교
            isAuthenticated = passwordEncoder.matches(user.getPassword(), findUser.getPasswordHash());
        }

        if (isAuthenticated) {
            // 로그인 성공 시 사용자 정보를 반환
            return ResponseEntity.ok(new UserResponseDto(findUser.getEmail(), findUser.getUsername()));
        } else {
            return ResponseEntity.status(401).body("로그인 실패: 잘못된 이메일 또는 비밀번호");
        }
    }
}
