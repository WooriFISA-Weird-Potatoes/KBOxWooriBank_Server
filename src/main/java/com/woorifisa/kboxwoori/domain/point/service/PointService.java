package com.woorifisa.kboxwoori.domain.point.service;

import com.woorifisa.kboxwoori.domain.point.dto.PointHistoryDTO;
import com.woorifisa.kboxwoori.domain.point.entity.Point;
import com.woorifisa.kboxwoori.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PointService {

   private final PointRepository pointRepository;

    @Transactional
    public PointHistoryDTO getPointHistory(String userId){
        List<Point> pointHistory = pointRepository.findPointByUserId(userId);
        PointHistoryDTO pointHistoryDTO = new PointHistoryDTO();

        pointHistoryDTO.setPoint(pointHistory.get(0).getUser().getPoint());
        pointHistoryDTO.setPointList(pointHistory);

        return pointHistoryDTO;

    }


}
