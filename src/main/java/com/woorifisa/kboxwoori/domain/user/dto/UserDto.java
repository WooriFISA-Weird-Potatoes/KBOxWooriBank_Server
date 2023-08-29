package com.woorifisa.kboxwoori.domain.user.dto;

import com.woorifisa.kboxwoori.domain.user.entity.Club;
import com.woorifisa.kboxwoori.domain.user.entity.Gender;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
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


    //DTO -> Entity
    public User toEntity() {
        User user;
        user = User.builder()
                .admin(false)
                .userId(userId)
                .password(password)
                .name(name)
                .gender(gender)
                .birth(birth)
                .phone(phone)
                .addr(addr)
                .club(club)
                .point(0)
                .svcAgmt(svcAgmt)
                .infoAgmt(infoAgmt)
                .wooriLinked(false)
                .build();
        return user;
    }
}
