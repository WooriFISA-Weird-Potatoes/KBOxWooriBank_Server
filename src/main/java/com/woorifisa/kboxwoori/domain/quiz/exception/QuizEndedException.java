package com.woorifisa.kboxwoori.domain.quiz.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class QuizEndedException extends CustomException {
    public static final CustomException EXCEPTION = new QuizEndedException();

    private QuizEndedException() {
        super(CustomExceptionStatus.QUIZ_ENDED);
    }
}
