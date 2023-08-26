package com.woorifisa.kboxwoori.domain.event.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class NotAuthenticatedAccoutException extends CustomException {
    public static final CustomException EXCEPTION = new NotAuthenticatedAccoutException();

    private NotAuthenticatedAccoutException() {
        super(CustomExceptionStatus.NOT_AUTHENTICATED_ACCOUNT);
    }
}
