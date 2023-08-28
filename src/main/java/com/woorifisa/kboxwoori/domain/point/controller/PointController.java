package com.woorifisa.kboxwoori.domain.point.controller;

import com.woorifisa.kboxwoori.domain.point.dto.PointHistoryDTO;
import com.woorifisa.kboxwoori.domain.point.service.PointService;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointController {

    @Autowired
    PointService pointService;

    @GetMapping("/api/point")
    public PointHistoryDTO getPointHistory(@AuthenticationPrincipal PrincipalDetails pdetails){
        PointHistoryDTO pointHistoryDTO = pointService.getPointHistory(pdetails.getUsername());
        return pointHistoryDTO;
    }


}
