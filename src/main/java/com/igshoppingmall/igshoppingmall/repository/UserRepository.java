package com.igshoppingmall.igshoppingmall.repository;

import com.igshoppingmall.igshoppingmall.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 검색
    User findByEmail(String email);
}
