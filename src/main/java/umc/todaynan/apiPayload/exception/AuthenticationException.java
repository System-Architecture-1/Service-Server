package umc.todaynan.apiPayload.exception;

import umc.todaynan.apiPayload.code.BaseErrorCode;
import umc.todaynan.apiPayload.code.status.ErrorStatus;

public class AuthenticationException extends RuntimeException {
    private BaseErrorCode code;

    public AuthenticationException(BaseErrorCode code) {
        super(code.getReason().getMessage());
        this.code = code;
    }

    public ErrorStatus getErrorCode() {
        return (ErrorStatus)code;
    }
}
