package com.Hy.ass.entity;

import jakarta.persistence.*;
import lombok.*;

// 🔥 核心修复：必须加上 @Entity 注解，JPA 才能识别并管理这个类，否则会报 Not a managed type 错误
@Entity
@Table(name = "score")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_name", nullable = false, length = 50)
    private String studentName;

    @Column(name = "course_name", nullable = false, length = 50)
    private String courseName;

    @Column(nullable = false)
    private Integer score;

    // 补全标准 Getter 方法，彻底解决 Lombok 在部分 IDEA 上的兼容性问题
    public Integer getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getScore() {
        return score;
    }
}