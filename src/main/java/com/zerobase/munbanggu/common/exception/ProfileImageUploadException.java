package com.zerobase.munbanggu.common.exception;

import com.zerobase.munbanggu.common.type.ErrorCode;
import lombok.Getter;

import java.io.IOException;

@Getter
public class ProfileImageUploadException extends RuntimeException {
    private final ErrorCode errorCode;
    private final IOException originalException;

    public ProfileImageUploadException(ErrorCode errorCode, IOException originalException) {
        super(errorCode.getDescription(), originalException);
        this.errorCode = errorCode;
        this.originalException = originalException;
    }

}
