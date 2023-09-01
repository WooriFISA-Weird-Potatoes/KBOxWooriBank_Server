package com.woorifisa.kboxwoori.domain.point.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class InsufficientPointsException extends CustomException {
    public static final CustomException EXCEPTION = new InsufficientPointsException();

    private InsufficientPointsException(){
        super(CustomExceptionStatus.INSUFFICIENT_POINTS_EXCEPTION);
    }
}
