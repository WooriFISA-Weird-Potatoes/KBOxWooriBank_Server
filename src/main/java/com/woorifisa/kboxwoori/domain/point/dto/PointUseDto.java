package com.woorifisa.kboxwoori.domain.point.dto;

import com.woorifisa.kboxwoori.domain.point.entity.Point;
import com.woorifisa.kboxwoori.domain.point.entity.PointStatus;
import com.woorifisa.kboxwoori.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointUseDto {
    private User user;
    private PointStatus statusCode;
    private Integer point;
    private LocalDateTime createdAt;

    public Point toEntity(){
        Point pointentity;
        pointentity = Point.builder()
                .user(user)
                .statusCode(statusCode)
                .point(point)
                .createdAt(createdAt)
                .build();
        return pointentity;
    }

}
