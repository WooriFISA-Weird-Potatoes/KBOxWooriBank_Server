package com.woorifisa.kboxwoori.domain.quiz.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class QuizNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new QuizNotFoundException();

    private QuizNotFoundException() {
        super(CustomExceptionStatus.QUIZ_NOT_FOUND);
    }
}
