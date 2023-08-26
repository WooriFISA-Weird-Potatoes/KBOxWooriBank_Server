package com.woorifisa.kboxwoori.domain.event.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class WooriLinkRequiredException extends CustomException {
    public static final CustomException EXCEPTION = new WooriLinkRequiredException();

    private WooriLinkRequiredException() {
        super(CustomExceptionStatus.WOORI_LINK_REQUIRED);
    }
}
