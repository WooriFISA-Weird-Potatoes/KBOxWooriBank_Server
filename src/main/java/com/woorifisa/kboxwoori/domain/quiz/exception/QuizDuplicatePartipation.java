package com.woorifisa.kboxwoori.domain.quiz.exception;

import com.woorifisa.kboxwoori.global.exception.CustomException;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;

public class QuizDuplicatePartipation extends CustomException {
    public static final CustomException EXCEPTION = new QuizDuplicatePartipation();

    private QuizDuplicatePartipation() {
        super(CustomExceptionStatus.QUIZ_DUPLICATE_PARTICIPATION);
    }
}
