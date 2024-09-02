package com.mtvs.sciencemuseum.domain.user.exception;

import com.mtvs.sciencemuseum.common.exception.ErrorCode;
import com.mtvs.sciencemuseum.common.exception.RootException;
import lombok.Getter;

@Getter
public class DuplicatedUsernameException extends RootException {

    private final String message;
    public DuplicatedUsernameException(String message, String logMessage) {
        super(ErrorCode.DUPLICATED_USER, message, logMessage);

        this.message = message;
    }
}
