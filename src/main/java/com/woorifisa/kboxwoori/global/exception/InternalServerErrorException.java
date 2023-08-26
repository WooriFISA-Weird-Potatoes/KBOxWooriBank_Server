package com.woorifisa.kboxwoori.global.exception;

public class InternalServerErrorException extends CustomException {
    public static final CustomException EXCEPTION = new InternalServerErrorException();

    private InternalServerErrorException() {
        super(CustomExceptionStatus.INTERNAL_SERVER_ERROR);
    }
}
