package com.shiaofuk.sqlserver.service.user;



public enum LoginState {
    SUCCESS("success"),
    ERROR("error"),  // 模糊的错误
    INVALID_USER("invalid user"),
    LOGIN_WRONG_PASSWORD("wrong password"),
    REGISTER_ERROR("register error"),
    TOKEN_INVALID("token invalid");

    final String message;


    LoginState(String message) {
        this.message = message;
    }
}
