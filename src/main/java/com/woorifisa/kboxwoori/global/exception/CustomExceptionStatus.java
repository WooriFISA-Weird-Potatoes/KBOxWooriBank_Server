package com.woorifisa.kboxwoori.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {
    SUCCESS(1000, true, "요청에 성공하였습니다."),
    BAD_REQUEST(1001, false, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(1002, false, "서버 내부에서 문제가 발생하였습니다."),
    REQUEST_ERROR(1003, false, "올바르지 않은 입력 값입니다."),

    NOT_AUTHENTICATED_ACCOUNT(1100, false, "로그인이 필요합니다."),
    DUPLICATED_USERID(1101, false, "이미 사용중인 아이디입니다."),

    ACCOUNT_NOT_FOUND(1200, false, "사용자를 찾을 수 없습니다."),

    ONGOING_EVENT_NOT_FOUND(1300, false, "현재 진행중인 이벤트가 없습니다."),
    WOORI_LINK_REQUIRED(1301, false, "우리 은행 계정 연동이 필요합니다."),
    INVALID_EVENT_PARTICIPATION_TIME( 1302, false, "이벤트 참여 시간이 아닙니다."),
    EVENT_IS_ENDED(1303, false, "이벤트가 마감되었습니다."),
    DUPLICATE_PARTICIPATION(1304, false, "같은 이벤트에 중복 참여는 불가합니다."),
    WINNING_RECORD_NOT_FOUND(1305, false, "당첨 내역이 없습니다."),

    INVALID_PREDICTION_PARTICIPATION_TIME(1400, false, "승부예측 참여 시간이 아닙니다."),
    ALL_PREDICTIONS_REQUIRED(1401, false, "모든 경기 예측 후 참여 가능합니다."),
    PARTICIPATION_RECORD_NOT_FOUND(1402, false, "참여 내역이 없습니다."),

    QUIZ_ENDED(1500, false, "퀴즈 참여 시간이 아닙니다."),
    QUIZ_NOT_FOUND(1501, false, "현재 진행중인 퀴즈가 없습니다."),
    QUIZ_DUPLICATE_PARTICIPATION(1502, false, "같은 퀴즈에 중복 참여는 불가합니다."),

    FILE_UPLOAD(1600, false, "파일 업로드에 실패헸습니다."),

    INSUFFICIENT_POINTS_EXCEPTION(1700, false, "잔여 포인트가 부족합니다."),

    CRAWLING_DATA_NOT_FOUND(1800, false, "크롤링할 데이터를 찾을 수 없습니다."),
    CRAWLING_DATA_CAN_NOT_SAVE(1801, false, "크롤링한 데이터를 저장할 수 없습니다."),
    CRAWLING_STORED_DATA_NOT_FOUND(1802, false, "크롤링한 데이터를 가져올 수 없습니다."),

    NOTIFICATION_NOT_FOUND(1900, false, "알림이 존재하지 않습니다.");

    private final int status;
    private final boolean success;
    private final String message;
}
