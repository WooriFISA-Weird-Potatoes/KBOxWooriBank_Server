package com.woorifisa.kboxwoori.domain.point.dto;

import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPointResponseDto {
    private Integer point;

    public User toEntity(){
        User user;
        user = User.builder()
                .point(point)
                .build();
        return user;
    }
}
