package com.woorifisa.kboxwoori.domain.point.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "point_history")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointStatus statusCode;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Builder
    public Point(Long id, User user, PointStatus statusCode, Integer point, LocalDate createdAt) {
        this.id = id;
        this.user = user;
        this.statusCode = statusCode;
        this.point = point;
        this.createdAt = createdAt;
    }

}
