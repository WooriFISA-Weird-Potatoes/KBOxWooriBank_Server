package com.woorifisa.kboxwoori.domain.admin.controller;

import com.woorifisa.kboxwoori.domain.admin.dto.PredictionResponseDto;
import com.woorifisa.kboxwoori.domain.admin.dto.QuizResponseDto;
import com.woorifisa.kboxwoori.domain.admin.dto.UserResponseDto;
import com.woorifisa.kboxwoori.domain.admin.service.UserStatisticsService;
import com.woorifisa.kboxwoori.domain.event.entity.Event;
import com.woorifisa.kboxwoori.domain.quiz.entity.Quiz;
import com.woorifisa.kboxwoori.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class UserStatisticsController {
    private final UserStatisticsService userStatisticsService;

    @GetMapping("/users/weekly")
    public ResponseDto<List<UserResponseDto>> getUserStatisticsForLast7Days(){
        return ResponseDto.success(userStatisticsService.getUserStatisticsForLast7Days());
    }
    @GetMapping("/quiz/weekly")
    public ResponseDto<List<QuizResponseDto>> getQuizStatisticsForLast7Days() {
        return ResponseDto.success(userStatisticsService.getQuizStatisticsForLast7Days());
    }
    @GetMapping("/prediction/weekly")
    public ResponseDto<List<PredictionResponseDto>> getPredictionStatisticsForLast7Days(){
        return ResponseDto.success(userStatisticsService.getPredictionStatisticsForLast7Days());
    }

    @GetMapping("/users/monthly")
    public ResponseDto<List<UserResponseDto>> gUserStatisticsForLast30Days(){
        return ResponseDto.success(userStatisticsService.getUserStatisticsForLast30Days());
    }

    @GetMapping("/quiz/monthly")
    public ResponseDto<List<QuizResponseDto>> getQuizStatisticsForLast30Days(){
        return ResponseDto.success(userStatisticsService.getQuizStatisticsForLast30Days());
    }
    @GetMapping("/prediction/monthly")
    public ResponseDto<List<PredictionResponseDto>> getPredictionStatisticsForLast30Days(){
        return ResponseDto.success(userStatisticsService.getPredictionStatisticsForLast30Days());
    }

    @PostMapping("/upload/quiz")
    public ResponseDto<List<Quiz>> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException{
        List<Quiz> quizList = userStatisticsService.processQuizFileUpload(file);
        return ResponseDto.success(quizList);
    }

    @PostMapping("/upload/product")
    public ResponseDto<List<Event>> handleProductFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        List<Event> eventList = userStatisticsService.processEventFileUpload(file);
        return ResponseDto.success(eventList);
    }
}

