package com.woorifisa.kboxwoori.domain.prediction.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class ParticipationRecordNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new ParticipationRecordNotFoundException();

    private ParticipationRecordNotFoundException() {
        super(CustomExceptionStatus.PARTICIPATION_RECORD_NOT_FOUND);
    }
}
