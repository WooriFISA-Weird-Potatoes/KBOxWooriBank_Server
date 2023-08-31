package com.woorifisa.kboxwoori.domain.crawling.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class CrawlingDataNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new CrawlingDataNotFoundException();
    private CrawlingDataNotFoundException() {
        super(CustomExceptionStatus.CRAWLING_DATA_NOT_FOUND);
    }
}
