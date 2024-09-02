package com.mtvs.sciencemuseum.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "Unknown error", "예상치 못한 오류가 발생했습니다.", "UNKNOWN-001"),

    /*USER 도메인*/
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "invalid user", "회원 정보가 없습니다.", "MEMBER-001"),
    DUPLICATED_USER(HttpStatus.CONFLICT, "Duplicated item", "이미 존재하는 아이디입니다.", "MEMBER-002"),


    /*AUTH 도메인*/
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "Authentication failed", "비밀번호가 잘못되었습니다.", "AUTH-001"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "Invalid token", "토큰 형식이 잘못되었습니다.", "AUTH-002"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Expired token", "토큰이 만료됬습니다.", "AUTH-003"),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "login required", "로그인 후 이용이 가능합니다.", "AUTH-005");


    private final String description;
    private final HttpStatus status;
    private final String code;
    private final String errorType;

    ErrorCode(HttpStatus status, String errorType, String description, String code) {
        this.status = status;
        this.errorType = errorType;
        this.description = description;
        this.code = code;
        }


    }
