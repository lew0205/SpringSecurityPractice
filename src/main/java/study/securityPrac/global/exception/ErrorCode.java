package study.securityPrac.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),
    ;
    public String msg;
    public int code;
}
