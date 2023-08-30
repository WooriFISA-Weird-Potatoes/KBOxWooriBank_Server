package com.woorifisa.kboxwoori.domain.point.controller;

import com.woorifisa.kboxwoori.domain.point.dto.PointHistoryDto;
import com.woorifisa.kboxwoori.domain.point.dto.UserPointResponseDto;
import com.woorifisa.kboxwoori.domain.point.service.PointService;
import com.woorifisa.kboxwoori.global.config.security.PrincipalDetails;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point")
public class PointController {

    private final PointService pointService;

    @GetMapping
    public ResponseDto<PointHistoryDto> getPointHistory(@AuthenticationPrincipal PrincipalDetails pdetails){
        PointHistoryDto pointHistoryDTO = pointService.getPointHistory(pdetails.getUsername());
        return ResponseDto.success(pointHistoryDTO);
    }

    @GetMapping("/honeymoney")
    public ResponseDto<UserPointResponseDto> getUserPoint(@AuthenticationPrincipal PrincipalDetails pdetails){
        UserPointResponseDto userPointResponseDTO = pointService.getUserPoint(pdetails.getUsername());
        return ResponseDto.success(userPointResponseDTO);
    }

    @PostMapping("/honeymoney")
    public ResponseDto usePoint(@AuthenticationPrincipal PrincipalDetails pdetails, @RequestBody UserPointResponseDto userPointResponseDTO){
        pointService.usePoint(pdetails.getUsername(), userPointResponseDTO);
        return ResponseDto.success();

    }


}