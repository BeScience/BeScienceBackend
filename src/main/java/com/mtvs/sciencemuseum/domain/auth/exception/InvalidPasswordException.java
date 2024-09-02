package com.mtvs.sciencemuseum.domain.auth.exception;

import com.mtvs.sciencemuseum.common.exception.ErrorCode;
import com.mtvs.sciencemuseum.common.exception.RootException;
import lombok.Getter;

@Getter
public class InvalidPasswordException extends RootException {
    private final String message;
    public InvalidPasswordException(String message, String logMessage) {
        super(ErrorCode.WRONG_PASSWORD, logMessage, message);

        this.message = message;
    }
}
