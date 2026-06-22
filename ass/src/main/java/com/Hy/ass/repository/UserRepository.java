package com.Hy.ass.repository;

import com.Hy.ass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 显式声明该方法，Spring Data JPA 会自动根据方法名生成对应的 SQL 查询语句
    Optional<User> findByUsername(String username);
}