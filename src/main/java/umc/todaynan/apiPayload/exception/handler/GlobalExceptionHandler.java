package umc.todaynan.apiPayload.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.BaseErrorCode;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.exception.AuthenticationException;
import umc.todaynan.apiPayload.exception.GeneralException;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        ErrorStatus errorStatus = ex.getErrorCode(); // ErrorStatus 가져오기
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.onFailure(errorStatus.getCode(),
                        ex.getMessage(),errorStatus.getMessage()));
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<String>> handleInternalException(GeneralException ex){
        ErrorStatus errorStatus = ex.getErrorCode(); // ErrorStatus 가져오기
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.onFailure(errorStatus.getCode(),
                        ex.getMessage(), errorStatus.getMessage()));
    }


}