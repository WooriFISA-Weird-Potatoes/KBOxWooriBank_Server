package com.woorifisa.kboxwoori.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private CustomExceptionStatus customExceptionStatus;
}
