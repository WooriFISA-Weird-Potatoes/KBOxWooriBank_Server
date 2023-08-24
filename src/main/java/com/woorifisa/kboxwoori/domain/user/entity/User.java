package com.woorifisa.kboxwoori.domain.user.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean admin;

    @Size(max = 100)
    @Column(nullable = false, unique = true)
    private String userId;

    @Size(max = 100)
    @Column(nullable = false)
    private String password;

    @Size(max = 100)
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birth;

    @Size(max = 20)
    @Column(nullable = false)
    private String phone;

    @Size(max = 100)
    @Column(nullable = false)
    private String addr;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Club club;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Boolean svcAgmt;

    @Column(nullable = false)
    private Boolean infoAgmt;

    @Column(nullable = false)
    private Boolean wooriLinked;

    @Builder
    public User(Boolean admin, String userId, String password, String name, Gender gender, LocalDate birth, String phone, String addr, Club club, Integer point, Boolean svcAgmt, Boolean infoAgmt, Boolean wooriLinked) {
        this.admin = admin;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.addr = addr;
        this.club = club;
        this.point = point;
        this.svcAgmt = svcAgmt;
        this.infoAgmt = infoAgmt;
        this.wooriLinked = wooriLinked;
    }
}