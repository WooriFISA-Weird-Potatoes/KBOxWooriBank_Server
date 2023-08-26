package com.woorifisa.kboxwoori.domain.event.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class EventIsEndedException extends CustomException {
    public static final CustomException EXCEPTION = new EventIsEndedException();

    private EventIsEndedException() {
        super(CustomExceptionStatus.EVENT_IS_ENDED);
    }
}
