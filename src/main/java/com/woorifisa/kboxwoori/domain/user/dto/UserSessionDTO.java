package com.woorifisa.kboxwoori.domain.user.dto;

import com.woorifisa.kboxwoori.domain.user.entity.Club;
import com.woorifisa.kboxwoori.domain.user.entity.Gender;
import com.woorifisa.kboxwoori.domain.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;

public class UserSessionDTO implements Serializable {
    private String userId;
    private String password;
    private String name;
    private Gender gender;
    private LocalDate birth;
    private String phone;
    private String addr;
    private Club club;
    private Integer point;
    private Boolean svcAgmt;
    private Boolean infoAgmt;
    private Boolean wooriLinked;

    //Entity -> DTO
    public UserSessionDTO(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.gender = user.getGender();
        this.birth = user.getBirth();
        this.phone = user.getPhone();
        this.addr = user.getAddr();
        this.club = user.getClub();
        this.point = user.getPoint();
        this.svcAgmt = user.getSvcAgmt();
        this.infoAgmt = user.getInfoAgmt();
        this.wooriLinked = user.getWooriLinked();
    }
}
