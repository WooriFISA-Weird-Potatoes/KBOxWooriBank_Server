package com.woorifisa.kboxwoori.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.woorifisa.kboxwoori.global.exception.CustomExceptionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto<T> {
    private int status;
    private boolean success;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static ResponseDto success() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(CustomExceptionStatus.SUCCESS.getStatus());
        responseDto.setSuccess(CustomExceptionStatus.SUCCESS.isSuccess());
        responseDto.setMessage(CustomExceptionStatus.SUCCESS.getMessage());
        return responseDto;
    }

    public static <T> ResponseDto<T> success(T data) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setStatus(CustomExceptionStatus.SUCCESS.getStatus());
        responseDto.setSuccess(CustomExceptionStatus.SUCCESS.isSuccess());
        responseDto.setMessage(CustomExceptionStatus.SUCCESS.getMessage());
        responseDto.setData(data);
        return responseDto;
    }

    public static ResponseDto error(CustomExceptionStatus status) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(status.getStatus());
        responseDto.setSuccess(status.isSuccess());
        responseDto.setMessage(status.getMessage());
        return responseDto;
    }
}
