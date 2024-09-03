package com.mtvs.sciencemuseum.domain.auth.service;

import com.mtvs.sciencemuseum.domain.auth.dto.CustomUserDetails;
import com.mtvs.sciencemuseum.domain.user.entity.User;
import com.mtvs.sciencemuseum.domain.user.exception.UserNotFoundException;
import com.mtvs.sciencemuseum.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ", "[AUTH] login fail. user: "+ username));

        return new CustomUserDetails(user);
    }
}
