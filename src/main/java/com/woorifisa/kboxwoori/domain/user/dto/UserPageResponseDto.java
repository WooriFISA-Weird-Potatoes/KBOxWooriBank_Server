package com.woorifisa.kboxwoori.domain.user.dto;

import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.*;

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
