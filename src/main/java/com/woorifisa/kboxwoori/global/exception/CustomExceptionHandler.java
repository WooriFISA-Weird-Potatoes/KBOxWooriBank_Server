package com.woorifisa.kboxwoori.global.exception;

import com.woorifisa.kboxwoori.global.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDto<?> validationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach((error) -> {
                    errors.put(error.getField(), error.getDefaultMessage());
                });
        return ResponseDto.error(CustomExceptionStatus.REQUEST_ERROR, errors);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseDto customException(CustomException customException) {
        return ResponseDto.error(customException.getCustomExceptionStatus());
    }
}
