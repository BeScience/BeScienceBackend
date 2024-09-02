package com.mtvs.sciencemuseum.domain.auth.exception;

import com.mtvs.sciencemuseum.common.exception.ErrorCode;
import com.mtvs.sciencemuseum.common.exception.RootException;
import lombok.Getter;

@Getter
public class ExpiredTokenException extends RootException {
    private final String message;
    public ExpiredTokenException(String message, String logMessage) {
        super(ErrorCode.EXPIRED_TOKEN, logMessage, message);

        this.message = message;
    }
}
