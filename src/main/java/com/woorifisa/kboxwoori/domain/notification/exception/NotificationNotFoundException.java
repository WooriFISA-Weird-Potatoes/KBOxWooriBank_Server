package com.woorifisa.kboxwoori.domain.notification.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class NotificationNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new NotificationNotFoundException();

    private NotificationNotFoundException() {
        super(CustomExceptionStatus.NOTIFICATION_NOT_FOUND);
    }
}
