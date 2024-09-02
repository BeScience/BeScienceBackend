package com.mtvs.sciencemuseum.domain.auth.dto;

import com.mtvs.sciencemuseum.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    /*권한 가져오기*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add((GrantedAuthority) () -> user.getRole()
                .name());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /*계정 만료 여부*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*계정 잠김 여부*/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*자격증명(암호)가 만료됬는지 여부*/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*계정 사용 가능 여부*/
    @Override
    public boolean isEnabled() {
        return true;
    }
}
