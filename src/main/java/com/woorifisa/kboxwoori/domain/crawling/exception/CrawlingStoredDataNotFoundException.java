package com.woorifisa.kboxwoori.domain.crawling.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class CrawlingStoredDataNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new CrawlingStoredDataNotFoundException();

    private CrawlingStoredDataNotFoundException() {
        super(CustomExceptionStatus.CRAWLING_STORED_DATA_NOT_FOUND);
    }
}
