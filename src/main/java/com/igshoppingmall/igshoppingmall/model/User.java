package com.igshoppingmall.igshoppingmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Transient // JPA가 이 필드를 데이터베이스에 저장하지 않도록 설정
    private String password;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    // 기본 생성자
    public User() {
    }

    public User(String name, String passwordHash, String email, String username, Long id, String password) {
        this.name = name;
        this.password = password;
        this.passwordHash = passwordHash;
        this.email = email;
        this.username = username;
        this.id = id;
    }
}
