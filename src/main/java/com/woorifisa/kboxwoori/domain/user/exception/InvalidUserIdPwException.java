package com.woorifisa.kboxwoori.domain.user.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class InvalidUserIdPwException extends CustomException {
    public static final CustomException EXCEPTION = new InvalidUserIdPwException();

    private InvalidUserIdPwException() {
        super(CustomExceptionStatus.INVALID_USER_ID_PW);
    }
}
