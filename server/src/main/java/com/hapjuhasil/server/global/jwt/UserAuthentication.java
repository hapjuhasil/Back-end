package com.hapjuhasil.server.global.jwt;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

//사용자 인증을 나타냄
public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    //principal : 사용자의 주요 식별 정보(예 : 아이디나 이메일)
    //credentials : 사용자의 패스워드나 인증 토큰 등의 인증 정보
    //authorities : 사용자의 권한 정보를 받아와서 UsernamePasswordAuthenticationToken의 생성자를 호출하여 객체 초기화
    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
