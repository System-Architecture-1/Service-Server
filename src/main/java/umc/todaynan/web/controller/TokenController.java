package umc.todaynan.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.code.status.SuccessStatus;
import umc.todaynan.apiPayload.exception.AuthenticationException;
import umc.todaynan.converter.TokenConverter;
import umc.todaynan.oauth2.Token;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.repository.RefreshTokenRepository;
import umc.todaynan.web.dto.TokenDTO.TokenResponseDTO;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    private final RefreshTokenRepository refreshTokenRepository;


    /**
     * 서비스 이용중 JWT 만료시 Front에게 알리는 API
     */
    @GetMapping("/token/expired")
    public ApiResponse jwtTokenExpired() {
        return ApiResponse.onFailure(ErrorStatus.TOKEN_EXPIRED.getCode(),  ErrorStatus.TOKEN_EXPIRED.getMessage(), null);
    }

    /**
     * 현재 사용중인 JWT 검증 API
     */
    @GetMapping("/token/test")
    public String testJwtToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String email = tokenService.getUid(token);
        System.out.println(email);
        return email;
    }

    @GetMapping("/token/regenerate")
    public ResponseEntity<ApiResponse<TokenResponseDTO.TokenRefreshDTO>> refreshAuth(HttpServletRequest request) {
        String token = request.getHeader("Refresh");
        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            String savedRefreshToken = tokenService.getRefreshTokenFromRepository(email);
            if (savedRefreshToken != null && savedRefreshToken.equals(token)) {
                Token newToken = tokenService.generateToken(email, "USER"); //access token 재발급
                newToken.setRefreshToken(savedRefreshToken);
                return ResponseEntity.ok(ApiResponse.of(SuccessStatus.TOKEN_REFRESHED, tokenConverter.toTokenRefreshDTO(newToken)));
            }
        }

        throw new AuthenticationException(ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY); // Throw exception for invalid token
    }


}
