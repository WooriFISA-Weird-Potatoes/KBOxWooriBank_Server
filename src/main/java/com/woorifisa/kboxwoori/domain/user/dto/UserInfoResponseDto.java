package com.woorifisa.kboxwoori.domain.user.dto;

import com.woorifisa.kboxwoori.domain.user.entity.Club;
import com.woorifisa.kboxwoori.domain.user.entity.Gender;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
@Data
public class UserInfoResponseDto {
    private String userId;
    private String password;
    private String name;
    private Gender gender;
    private LocalDate birth;
    private String phone;
    private String addr;
    private Club club;

    public UserInfoResponseDto(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.gender = user.getGender();
        this.birth = user.getBirth();
        this.phone = user.getPhone();
        this.addr = user.getAddr();
        this.club = user.getClub();
    }

    public User toEntity() {
        User user;
        user = User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .gender(gender)
                .birth(birth)
                .phone(phone)
                .addr(addr)
                .club(club)
                .build();
        return user;
    }

}
