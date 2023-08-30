package com.woorifisa.kboxwoori.domain.admin.exception;

import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class FileUploadException extends RuntimeException{

    public FileUploadException(CustomExceptionStatus statusEnum){
        super(statusEnum.getMessage());
    }

    public FileUploadException(CustomExceptionStatus statusEnum,Throwable cause){
        super(statusEnum.getMessage(),cause);
    }
}
