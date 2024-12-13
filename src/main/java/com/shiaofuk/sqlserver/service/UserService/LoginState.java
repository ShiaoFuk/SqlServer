package com.shiaofuk.sqlserver.service.UserService;



public enum LoginState {
    SUCCESS("success"),
    INVALID_USER("invalid user"),
    LOGIN_WRONG_PASSWORD("wrong password"),
    REGISTER_ERROR("register error");


    final String message;


    LoginState(String message) {
        this.message = message;
    }
}
