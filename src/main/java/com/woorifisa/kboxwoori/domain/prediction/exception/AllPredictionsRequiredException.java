package com.woorifisa.kboxwoori.domain.prediction.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class AllPredictionsRequiredException extends CustomException {
    public static final CustomException EXCEPTION = new AllPredictionsRequiredException();

    private AllPredictionsRequiredException() {
        super(CustomExceptionStatus.ALL_PREDICTIONS_REQUIRED);
    }
}
