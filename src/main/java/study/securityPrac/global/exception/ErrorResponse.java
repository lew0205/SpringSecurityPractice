package study.securityPrac.global.exception;

import lombok.RequiredArgsConstructor;

public class ErrorResponse {
    private final String msg;
    private final int code;

    public ErrorResponse(ErrorCode errorCode) {
        this.msg = errorCode.msg;
        this.code = errorCode.code;
    }
}
