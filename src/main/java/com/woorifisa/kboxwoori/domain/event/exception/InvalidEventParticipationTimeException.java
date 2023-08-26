package com.woorifisa.kboxwoori.domain.event.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class InvalidEventParticipationTimeException extends CustomException {
    public static final CustomException EXCEPTION = new InvalidEventParticipationTimeException();

    private InvalidEventParticipationTimeException() {
        super(CustomExceptionStatus.INVALID_EVENT_PARTICIPATION_TIME);
    }
}
