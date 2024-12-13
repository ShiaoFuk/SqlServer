package com.shiaofuk.sqlserver.service.UserService;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LoginServiceRes {
    private final static Map<LoginState, LoginServiceRes> loginServices = new HashMap<>();
    LoginState loginState;
    String message;
    @Setter
    String token;

    static {
        // 注册或登录成功
        loginServices.put(LoginState.SUCCESS, new LoginServiceRes(LoginState.SUCCESS));
        // 账户密码不符合格式
        loginServices.put(LoginState.INVALID_USER, new LoginServiceRes(LoginState.INVALID_USER));
        // 登录密码错误
        loginServices.put(LoginState.LOGIN_WRONG_PASSWORD, new LoginServiceRes(LoginState.LOGIN_WRONG_PASSWORD));
        // 注册用户重复
        loginServices.put(LoginState.REGISTER_ERROR, new LoginServiceRes(LoginState.REGISTER_ERROR));
    }


    private LoginServiceRes(LoginState loginState) {
        this.loginState = loginState;
        this.message = loginState.message;
    }


    /**
     * 获取成功返回值实例，用于Service提供给Controller
     * @param token 登录或注册成功的token
     * @return LoginService实例，getMessage方法用于获取信息，getToken方法用于获取登录或注册成功的token
     */
    public static LoginServiceRes getSuccessRes(String token) {
        LoginServiceRes res = loginServices.get(LoginState.SUCCESS);
        res.setToken(token);
        return res;
    }


    /**
     * 获取失败返回值实例，用于Service提供给Controller
     * @param loginState 处理登录的状态
     * @return LoginService实例，getMessage方法用于获取信息
     */
    public static LoginServiceRes getErrorRes(LoginState loginState) {
        if (loginState.equals(LoginState.SUCCESS)) {
            throw new IllegalArgumentException("错误返回值不能传递成功的状态作为参数");
        }
        return loginServices.get(loginState);
    }

}