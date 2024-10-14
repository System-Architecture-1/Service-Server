package umc.todaynan.apiPayload.exception;

import umc.todaynan.apiPayload.code.BaseErrorCode;
import umc.todaynan.apiPayload.code.ErrorReasonDTO;
import umc.todaynan.apiPayload.code.status.ErrorStatus;

public class GeneralException extends RuntimeException {
    private BaseErrorCode code;

    public GeneralException(BaseErrorCode code) {
        super(code.getReason().getMessage());
        this.code = code;
    }

    public ErrorStatus getErrorCode() {
        return (ErrorStatus)code;
    }

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
