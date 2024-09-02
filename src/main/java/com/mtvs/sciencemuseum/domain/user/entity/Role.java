package com.mtvs.sciencemuseum.domain.user.entity;

public enum Role {
    USER("ROLE_USER"),// 일반 회원
    ADMIN("ROLE_ADMIN");// 관리자

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
