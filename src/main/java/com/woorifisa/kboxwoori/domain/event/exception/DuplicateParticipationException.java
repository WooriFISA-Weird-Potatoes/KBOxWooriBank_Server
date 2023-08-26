package com.woorifisa.kboxwoori.domain.event.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class DuplicateParticipationException extends CustomException {
    public static final CustomException EXCEPTION = new DuplicateParticipationException();

    private DuplicateParticipationException() {
        super(CustomExceptionStatus.DUPLICATE_PARTICIPATION);
    }
}
