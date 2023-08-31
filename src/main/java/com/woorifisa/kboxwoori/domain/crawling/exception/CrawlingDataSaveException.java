package com.woorifisa.kboxwoori.domain.crawling.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class CrawlingDataSaveException extends CustomException {
    public static final CustomException EXCEPTION = new CrawlingDataSaveException();

    private CrawlingDataSaveException() {
        super(CustomExceptionStatus.CRAWLING_DATA_CAN_NOT_SAVE);
    }
}
