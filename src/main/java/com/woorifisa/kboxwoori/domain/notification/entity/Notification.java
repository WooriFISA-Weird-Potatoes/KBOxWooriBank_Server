package com.woorifisa.kboxwoori.domain.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isChecked;

    private Long metadata;

    @Builder
    public Notification(User user, Type type, LocalDateTime createdAt, Boolean isChecked, Long metadata) {
        this.user = user;
        this.type = type;
        this.createdAt = createdAt;
        this.isChecked = isChecked;
        this.metadata = metadata;
    }
}
