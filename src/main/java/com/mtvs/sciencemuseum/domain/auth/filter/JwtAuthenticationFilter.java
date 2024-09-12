package com.mtvs.sciencemuseum.domain.auth.filter;

import com.mtvs.sciencemuseum.domain.auth.dto.CustomUserDetails;
import com.mtvs.sciencemuseum.domain.auth.dto.LoginedInfo;
import com.mtvs.sciencemuseum.domain.auth.exception.ExpiredTokenException;
import com.mtvs.sciencemuseum.domain.user.entity.Role;
import com.mtvs.sciencemuseum.domain.user.entity.User;
import com.mtvs.sciencemuseum.util.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        /*토큰이 있는지 검증함*/
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("[JWT] 토큰이 존재합니다. 토큰을 검증합니다.");

        String token = authorization.split(" ")[1];

        /*access token 여부 검증*/
        /*토큰 만료 여부 확인*/
        if(jwtProvider.isExpired(token)) {
            log.info("[JWT] access 토큰 만료: {}", jwtProvider.isExpired(token));
            throw new ExpiredTokenException("토큰이 만료됬습니다.", "Access Token 만료됨.");
        }

        String username = jwtProvider.getNickname(token);
        Role roles = Role.valueOf(jwtProvider.getRoles(token));
        LoginedInfo loginedInfo = new LoginedInfo();
        User member = new User();

        member.setUsername(username);
        member.setRole(roles);

        loginedInfo.setUsername(username);
        loginedInfo.setRole(roles);
        loginedInfo.setIsLogin(true);

        CustomUserDetails customMemberDetails = new CustomUserDetails(member);
        Authentication authToken = new UsernamePasswordAuthenticationToken(loginedInfo, null, customMemberDetails.getAuthorities());

        log.info("[JWT] 토큰 검증 성공. 사용자: {}", username);

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
