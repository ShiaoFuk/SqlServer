package com.shiaofuk.sqlserver.controller;

import com.shiaofuk.sqlserver.bean.UserPermission;
import com.shiaofuk.sqlserver.bean.result.ErrorResult;
import com.shiaofuk.sqlserver.bean.result.Result;
import com.shiaofuk.sqlserver.bean.result.SuccessResult;
import com.shiaofuk.sqlserver.dto.MyRequestBody;
import com.shiaofuk.sqlserver.dto.user.LoginUser;
import com.shiaofuk.sqlserver.dto.user.UserInfo;
import com.shiaofuk.sqlserver.mapper.UserMapper;
import com.shiaofuk.sqlserver.model.User;
import com.shiaofuk.sqlserver.service.user.UserServiceRes;
import com.shiaofuk.sqlserver.service.user.LoginState;
import com.shiaofuk.sqlserver.service.user.UserService;
import com.shiaofuk.sqlserver.utils.JwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 用户信息
 */
@RequestMapping("/user")
@RestController
public class UserController {
    private final JwtUtil jwtUtil;
    UserService userService;
    UserMapper userMapper;
    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil, UserMapper userMapper) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
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


    /**
     * 获取所有用户
     * @param requestBody 传入权限
     */
    @PostMapping("/getUser")
    public Result<List<User>> getUser(@Valid@RequestBody MyRequestBody<UserPermission> requestBody) {
        Integer userId = jwtUtil.verifyToken(requestBody.getToken());
        if (userId == null) {
            return new ErrorResult<>();
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null || user.getPermission() < UserPermission.ROOT.getVal()) {
            return new ErrorResult<>();
        }
        List<User> userList = userMapper.selectAllByPermission(requestBody.getData().getVal());
        if (userList == null) {
            return new ErrorResult<>();
        } else if (userList.isEmpty()) {
            return new ErrorResult<>("当前没有此类型成员");
        }
        return new SuccessResult<>(userList);
    }

    @PostMapping("/getUserInfo")
    public Result<User> getUseInfo(@NotBlank@RequestBody String token) {
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return new ErrorResult<>();
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return new ErrorResult<>();
        }
        user.setPassword(null);
        return new SuccessResult<>(user);
    }


}
