package com.woorifisa.kboxwoori.global.exception;

import com.woorifisa.kboxwoori.global.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    //TODO: validation error handler 추가

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDto customException(CustomException customException) {
        return ResponseDto.error(customException.getCustomExceptionStatus());
    }
}
