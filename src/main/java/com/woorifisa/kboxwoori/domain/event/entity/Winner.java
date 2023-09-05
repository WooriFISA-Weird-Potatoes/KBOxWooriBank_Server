package com.woorifisa.kboxwoori.domain.event.entity;

import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Winner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "eventId")
    Event event;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    String addr;

    public void updateAddr(String addr) {
        this.addr = addr;
    }
}
