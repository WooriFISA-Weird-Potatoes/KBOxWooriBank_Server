package com.woorifisa.kboxwoori.domain.user.dto;

import com.woorifisa.kboxwoori.domain.user.entity.Club;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPageResponseDto {
    private String clubName;
    private String name;
    private Integer point;
    private Integer predictedResult;

    public UserPageResponseDto(User user){
        this.clubName = user.getClub().getShortName();
        this.name = user.getName();
        this.point = user.getPoint();
    }
}
