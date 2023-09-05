package com.woorifisa.kboxwoori.domain.event.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class WinningRecordNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new WinningRecordNotFoundException();

    private WinningRecordNotFoundException() {
        super(CustomExceptionStatus.WINNING_RECORD_NOT_FOUND);
    }
}
