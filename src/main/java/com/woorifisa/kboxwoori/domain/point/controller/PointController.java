package com.woorifisa.kboxwoori.domain.point.controller;

import com.woorifisa.kboxwoori.domain.point.dto.PointHistoryDto;
import com.woorifisa.kboxwoori.domain.point.dto.UserPointResponseDto;
import com.woorifisa.kboxwoori.domain.point.service.PointService;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.woorifisa.kboxwoori.global.util.AuthenticationUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point")
public class PointController {

    private final PointService pointService;

    @GetMapping
    public ResponseDto<PointHistoryDto> getPointHistory(){
        PointHistoryDto pointHistoryDTO = pointService.getPointHistory(getCurrentUserId());
        return ResponseDto.success(pointHistoryDTO);
    }

    @GetMapping("/honeymoney")
    public ResponseDto<UserPointResponseDto> getUserPoint(){
        UserPointResponseDto userPointResponseDTO = pointService.getUserPoint(getCurrentUserId());
        return ResponseDto.success(userPointResponseDTO);
    }

    @PostMapping("/honeymoney")
    public ResponseDto usePoint(@RequestBody UserPointResponseDto userPointResponseDTO){
        pointService.usePoint(getCurrentUserId(), userPointResponseDTO);
        return ResponseDto.success();

    }


}