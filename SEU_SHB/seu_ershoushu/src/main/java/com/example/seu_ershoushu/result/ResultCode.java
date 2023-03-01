package com.example.seu_ershoushu.result;

public enum ResultCode {
    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),//未授权
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    public final int code;

    ResultCode(int code) {
        this.code=code;
    }
}
