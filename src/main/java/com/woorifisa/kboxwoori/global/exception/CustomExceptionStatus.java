package com.woorifisa.kboxwoori.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus {
    SUCCESS(1000, true, "요청에 성공하였습니다."),
    BAD_REQUEST(1001, false, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(1002, false, "서버 내부에서 문제가 발생하였습니다."),

    NOT_AUTHENTICATED_ACCOUNT(1100, false, "로그인이 필요합니다."),

    ONGOING_EVENT_NOT_FOUND(1300, false, "현재 진행중인 이벤트가 없습니다."),
    WOORI_LINK_REQUIRED(1301, false, "우리 은행 계정 연동이 필요합니다."),
    INVALID_EVENT_PARTICIPATION_TIME( 1302, false, "이벤트 참여 시간이 아닙니다."),
    EVENT_IS_ENDED(1303, false, "이벤트가 마감되었습니다."),
    DUPLICATE_PARTICIPATION(1304, false, "같은 이벤트에 중복 참여는 불가합니다.");

    private final int status;
    private final boolean success;
    private final String message;
}
