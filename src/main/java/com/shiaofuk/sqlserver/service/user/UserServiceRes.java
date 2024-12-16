package com.shiaofuk.sqlserver.service.user;


import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserServiceRes {
    private final static Map<LoginState, UserServiceRes> loginServices = new HashMap<>();
    LoginState loginState;
    String message;
    @Setter
    String data;

    static {
        // 注册或登录成功
        loginServices.put(LoginState.SUCCESS, new UserServiceRes(LoginState.SUCCESS));
        // 模糊的错误
        loginServices.put(LoginState.ERROR, new UserServiceRes(LoginState.ERROR));
        // 账户密码不符合格式
        loginServices.put(LoginState.INVALID_USER, new UserServiceRes(LoginState.INVALID_USER));
        // 登录密码错误
        loginServices.put(LoginState.LOGIN_WRONG_PASSWORD, new UserServiceRes(LoginState.LOGIN_WRONG_PASSWORD));
        // 注册用户重复
        loginServices.put(LoginState.REGISTER_ERROR, new UserServiceRes(LoginState.REGISTER_ERROR));
        // token校验失败
        loginServices.put(LoginState.TOKEN_INVALID, new UserServiceRes(LoginState.TOKEN_INVALID));
    }


    private UserServiceRes(LoginState loginState) {
        this.loginState = loginState;
        this.message = loginState.message;
    }


    /**
     * 获取成功返回值实例，用于Service提供给Controller
     * @param token 登录或注册成功的token
     * @return LoginService实例，getMessage方法用于获取信息，getToken方法用于获取登录或注册成功的token
     */
    public static UserServiceRes getSuccessRes(@Nullable String token) {
        UserServiceRes res = loginServices.get(LoginState.SUCCESS);
        if (token != null)
            res.setData(token);
        return res;
    }


    /**
     * 获取失败返回值实例，用于Service提供给Controller
     * @param loginState 处理登录的状态
     * @return LoginService实例，getMessage方法用于获取信息
     */
    public static UserServiceRes getErrorRes(LoginState loginState) {
        if (loginState.equals(LoginState.SUCCESS)) {
            throw new IllegalArgumentException("错误返回值不能传递成功的状态作为参数");
        }
        return loginServices.get(loginState);
    }

}