package com.woorifisa.kboxwoori.global.exception.security;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class InvalidRefreshTokenException extends CustomException {
    public static final CustomException EXCEPTION = new InvalidRefreshTokenException();

    private InvalidRefreshTokenException() {
        super(CustomExceptionStatus.INVALID_REFRESH_TOKEN);
    }
}
