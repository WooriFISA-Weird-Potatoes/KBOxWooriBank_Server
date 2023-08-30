package com.woorifisa.kboxwoori.domain.prediction.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class InvalidPredictionParticipationTimeException extends CustomException {
    public static final CustomException EXCEPTION = new InvalidPredictionParticipationTimeException();

    private InvalidPredictionParticipationTimeException() {
        super(CustomExceptionStatus.INVALID_PREDICTION_PARTICIPATION_TIME);
    }
}
