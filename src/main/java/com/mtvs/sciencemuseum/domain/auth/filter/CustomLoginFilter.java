package com.mtvs.sciencemuseum.domain.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtvs.sciencemuseum.domain.auth.dto.CustomUserDetails;
import com.mtvs.sciencemuseum.domain.auth.dto.LoginRequestDTO;
import com.mtvs.sciencemuseum.domain.auth.dto.LoginResponseDTO;
import com.mtvs.sciencemuseum.domain.auth.exception.InvalidPasswordException;
import com.mtvs.sciencemuseum.domain.user.exception.UserNotFoundException;
import com.mtvs.sciencemuseum.util.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*로그인 필터*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        /*로그인 정보 가져옴*/
        try {
            LoginRequestDTO dto = objectMapper.readValue(request.getInputStream(), LoginRequestDTO.class);
            log.info("[AUTH] 로그인 요청: {}", dto.getUsername());

            /*검증용 객체에 담음*/
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword(), null);

            log.info("[AUTH] 로그인 요청2: {}", dto.getUsername());

            /*로그인 검증 진행*/
            return authenticationManager.authenticate(token);

        } catch (IOException e) {
            log.error("[AUTH] 로그인 인증 오류: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        } catch (InternalAuthenticationServiceException e) {
            log.error("[AUTH] 로그인 인증 오류: {}", e.getMessage());
            if(e.getCause() instanceof UserNotFoundException) {
                throw new UserNotFoundException("존재하지 않는 회원입니다.", "Login Fail: " + e.getMessage());
            } else{
                throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.", "Login Fail: " + e.getMessage());
            }
        }
    }

    /*로그인 성공시*/
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();
        log.info("[AUTH] 로그인 성공: {}, JWT 발급 진행", authResult.getName());

        /*권한 추출*/
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        SecurityContextHolder.getContext().setAuthentication(authResult);

        /*토큰 발급*/
        String accessToken = jwtProvider.createAccessToken(user.getUsername(), role);
        log.info("[JWT] JWT 발급 완료");

        /*토큰을 반환 객체에 담아서 반환*/
        LoginResponseDTO responseDto = new LoginResponseDTO();
        responseDto.setAccessToken(accessToken);

        String result = objectMapper.writeValueAsString(responseDto);
        response.getWriter().write(result);
    }

    /*로그인 실패시*/
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        log.error("[AUTH] 로그인 실패: {}", failed.getMessage());
        if(failed.getCause() instanceof UserNotFoundException) {
            throw new UserNotFoundException("존재하지 않는 회원입니다.", "Login Fail: " + failed.getMessage());
        } else{
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.", "Login Fail: " + failed.getMessage());
        }
    }
}
