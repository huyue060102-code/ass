package com.Hy.ass.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
}