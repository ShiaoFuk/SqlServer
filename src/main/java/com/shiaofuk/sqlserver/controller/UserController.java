package com.shiaofuk.sqlserver.controller;

import com.shiaofuk.sqlserver.bean.result.ErrorResult;
import com.shiaofuk.sqlserver.bean.result.Result;
import com.shiaofuk.sqlserver.bean.result.SuccessResult;
import com.shiaofuk.sqlserver.dto.User.LoginUser;
import com.shiaofuk.sqlserver.service.UserService.LoginServiceRes;
import com.shiaofuk.sqlserver.service.UserService.LoginState;
import com.shiaofuk.sqlserver.service.UserService.UserService;
import com.shiaofuk.sqlserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/user")
@RestController
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public Result<String> Login(@RequestBody LoginUser user) {
        LoginServiceRes res = userService.login(user);
        if (res.getLoginState() == LoginState.SUCCESS) {
            return new SuccessResult<>(res.getMessage(), res.getToken());
        }
        return new ErrorResult<>(res.getMessage(), res.getToken());
    }


    @PostMapping("/register")
    public Result<String> Register(@RequestBody LoginUser user) {
        LoginServiceRes res = userService.register(user);
        if (res.getLoginState() == LoginState.SUCCESS) {
            return new SuccessResult<>(res.getMessage(), res.getToken());
        }
        return new ErrorResult<>(res.getMessage(), res.getToken());
    }


}
