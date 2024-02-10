package com.hapjuhasil.server.global.jwt;

import com.hapjuhasil.server.config.ValueConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.hapjuhasil.server.global.jwt.JwtValidationType.VALID_JWT;
import static io.jsonwebtoken.lang.Strings.hasText;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter : 클라이언트 각 요청마다 한 번씩 호출

    private final JwtTokenProvider jwtTokenProvider;
    private final ValueConfig valueConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            val token = getAccessTokenFromRequest(request);
            if (hasText(token) && jwtTokenProvider.validateToken(token) == VALID_JWT) {
                val authentication = new UserAuthentication(getMemberId(token), null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        filterChain.doFilter(request, response);
    }



    private long getMemberId(String token) {
        return jwtTokenProvider.getUserFromJwt(token);
    } //사용자 ID 추출

    private String getAccessTokenFromRequest(HttpServletRequest request) { //클라이언트 요청에서 JWT 추출
        return isContainsAccessToken(request) ? getAuthorizationAccessToken(request) : null;
    }

    //라이언트 요청 헤더에 Authorization 헤더가 있는지 및 해당 헤더가 JWT를 포함하는지 확인
    private boolean isContainsAccessToken(HttpServletRequest request) {
        val authorization = request.getHeader(AUTHORIZATION);
        return authorization != null && authorization.startsWith(valueConfig.getBEARER_HEADER());
    }

    //클라이언트 요청에서 Authorization 헤더에서 JWT를 추출
    private String getAuthorizationAccessToken(HttpServletRequest request) {
        return getTokenFromBearerString(request.getHeader(AUTHORIZATION));
    }

    //Bearer 헤더의 값에서 실제 JWT를 추출
    private String getTokenFromBearerString(String token) {
        return token.replaceFirst(valueConfig.getBEARER_HEADER(), valueConfig.getBLANK());
    }
}
