package com.woorifisa.kboxwoori.domain.quiz.exception;

import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){

        super(CustomExceptionStatus.NOT_AUTHENTICATED_ACCOUNT.getMessage());
    }
}
