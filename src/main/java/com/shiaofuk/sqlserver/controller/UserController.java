package com.shiaofuk.sqlserver.controller;

import com.shiaofuk.sqlserver.bean.result.ErrorResult;
import com.shiaofuk.sqlserver.bean.result.Result;
import com.shiaofuk.sqlserver.bean.result.SuccessResult;
import com.shiaofuk.sqlserver.dto.user.LoginUser;
import com.shiaofuk.sqlserver.dto.user.UserInfo;
import com.shiaofuk.sqlserver.service.user.UserServiceRes;
import com.shiaofuk.sqlserver.service.user.LoginState;
import com.shiaofuk.sqlserver.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户信息
 */
@RequestMapping("/user")
@RestController
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * 登录
     * @param user 用户账号
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody@Valid LoginUser user) {
        UserServiceRes res = userService.login(user);
        if (res.getLoginState() == LoginState.SUCCESS) {
            return new SuccessResult<>(res.getMessage(), res.getData());
        }
        return new ErrorResult<>(res.getMessage(), res.getData());
    }

    /**
     * 注册
     * @param user 用户账号
     * @return
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody@Valid LoginUser user) {
        UserServiceRes res = userService.register(user);
        if (res.getLoginState() == LoginState.SUCCESS) {
            return new SuccessResult<>(res.getMessage(), res.getData());
        }
        return new ErrorResult<>(res.getMessage(), res.getData());
    }


    /**
     * 更新用户信息
     * @param userInfo 用户信息
     */
    @PostMapping("/updateInfo")
    public Result<String> updateUserInfo(@Valid@RequestBody UserInfo userInfo) {
        if (userInfo.getEmail() == null && userInfo.getCertificateNumber() == null) {
            return new ErrorResult<>("至少填写一个信息", null);
        }
        UserServiceRes res = userService.updateUserInfo(userInfo);
        if (res.getLoginState() == LoginState.SUCCESS) {
            return new SuccessResult<>(res.getMessage(), res.getData());
        }
        return new ErrorResult<>(res.getMessage(), res.getData());
    }

}
