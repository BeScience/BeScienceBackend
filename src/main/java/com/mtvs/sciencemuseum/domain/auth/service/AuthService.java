package com.mtvs.sciencemuseum.domain.auth.service;

import com.mtvs.sciencemuseum.domain.auth.dto.JoinRequestDTO;
import com.mtvs.sciencemuseum.domain.auth.dto.LoginedInfo;
import com.mtvs.sciencemuseum.domain.user.entity.User;
import com.mtvs.sciencemuseum.domain.user.exception.DuplicatedUsernameException;
import com.mtvs.sciencemuseum.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /*회원가입*/
    public void join(JoinRequestDTO dto){

        /*이름 중복여부 확인*/
        userRepository.findByUsername(dto.getUsername()).ifPresent(user -> {
            throw new DuplicatedUsernameException("이미 존재하는 이름입니다.", "[AUTH] 이름 중복: "+ dto.getUsername());
        });

        /*회원가입 객체 생성*/
        User user = new User(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword())
        );

        userRepository.save(user);
    }

    /*로그인 한 사용자 정보 가져옴.*/
    public LoginedInfo getLoginInfo(){
        Object principal = SecurityContextHolder.getContext().getAuthentication();

        if(principal == null){// 인증 전

            LoginedInfo anonymous = new LoginedInfo();
            anonymous.setUsername("__SYS__ANONYMOUS");
            anonymous.setIsLogin(false);
            return anonymous;
        }
        else{
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginedInfo){
                return (LoginedInfo)SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
            }
        }

        return null;
    }
}


