package com.woorifisa.kboxwoori.domain.point.dto;

import com.woorifisa.kboxwoori.domain.point.entity.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointHistoryDTO implements Serializable {
    private Integer point;
    private List<Point> pointList;


}
