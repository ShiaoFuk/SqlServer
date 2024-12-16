package com.shiaofuk.sqlserver.service.user;


import com.shiaofuk.sqlserver.dto.user.LoginUser;
import com.shiaofuk.sqlserver.dto.user.UserInfo;
import com.shiaofuk.sqlserver.repository.UserRepository;
import com.shiaofuk.sqlserver.utils.JwtUtil;
import com.shiaofuk.sqlserver.utils.UserValidCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    /**
     * 登录
     * @param loginUser 登录用户
     * @return LoginServiceRes 包含状态值
     */
    public UserServiceRes login(LoginUser loginUser) {
        if (!UserValidCheckUtils.checkUser(loginUser)) {
            return UserServiceRes.getErrorRes(LoginState.INVALID_USER);
        }
        Integer id = userRepository.verifyPassword(loginUser);
        if (id == null) {
            return UserServiceRes.getErrorRes(LoginState.LOGIN_WRONG_PASSWORD);
        }
        return UserServiceRes.getSuccessRes(jwtUtil.generateToken(id));
    }


    /**
     * 注册
     * @param registerUser 注册用户
     * @return LoginServiceRes 包含状态值
     */
    public UserServiceRes register(LoginUser registerUser) {
        if (!UserValidCheckUtils.checkUser(registerUser)) {
            return UserServiceRes.getErrorRes(LoginState.INVALID_USER);
        }
        Integer id = userRepository.registerUser(registerUser);
        if (id == null) {
            return UserServiceRes.getErrorRes(LoginState.REGISTER_ERROR);
        }
        return UserServiceRes.getSuccessRes(jwtUtil.generateToken(id));
    }


    /**
     * 更新用户信息
     * @param userInfo
     * @return 成功则返回一个只有message的，data为null的返回值，失败返回错误的
     */
    public UserServiceRes updateUserInfo(UserInfo userInfo) {
        Integer id = jwtUtil.verifyToken(userInfo.getToken());
        if (id == null) {
            return UserServiceRes.getErrorRes(LoginState.TOKEN_INVALID);
        }
        if (!userRepository.updateUserInfo(userInfo, id)) {
            // 更新出错
            return UserServiceRes.getErrorRes(LoginState.ERROR);
        }
        return UserServiceRes.getSuccessRes(null);
    }
}
