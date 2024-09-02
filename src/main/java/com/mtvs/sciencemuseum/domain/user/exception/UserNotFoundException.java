package com.mtvs.sciencemuseum.domain.user.exception;

import com.mtvs.sciencemuseum.common.exception.ErrorCode;
import com.mtvs.sciencemuseum.common.exception.RootException;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RootException {
    private final String message;

    public UserNotFoundException(String message, String logMessage) {
        super(ErrorCode.USER_NOT_FOUND, logMessage, message);
        this.message = message;
    }
}
