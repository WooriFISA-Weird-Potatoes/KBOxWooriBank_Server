package com.woorifisa.kboxwoori.domain.event.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class OngoingEventNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new OngoingEventNotFoundException();

    private OngoingEventNotFoundException() {
        super(CustomExceptionStatus.ONGOING_EVENT_NOT_FOUND);
    }
}
