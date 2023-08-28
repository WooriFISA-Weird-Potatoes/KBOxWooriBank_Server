package com.woorifisa.kboxwoori.domain.user.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class NotAuthenticatedAccountException extends CustomException {
    public static final CustomException EXCEPTION = new NotAuthenticatedAccountException();

    private NotAuthenticatedAccountException() {
        super(CustomExceptionStatus.NOT_AUTHENTICATED_ACCOUNT);
    }
}
