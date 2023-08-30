package com.woorifisa.kboxwoori.domain.quiz.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class AccountNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new AccountNotFoundException();

    private AccountNotFoundException() {
        super(CustomExceptionStatus.ACCOUNT_NOT_FOUND);
    }
}
