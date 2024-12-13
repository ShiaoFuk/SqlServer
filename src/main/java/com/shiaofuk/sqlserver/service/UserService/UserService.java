package com.shiaofuk.sqlserver.service.UserService;


import com.shiaofuk.sqlserver.dto.User.LoginUser;
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
    public LoginServiceRes login(LoginUser loginUser) {
        if (!UserValidCheckUtils.checkUser(loginUser)) {
            return LoginServiceRes.getErrorRes(LoginState.INVALID_USER);
        }
        Integer id = userRepository.verifyPassword(loginUser);
        if (id == null) {
            return LoginServiceRes.getErrorRes(LoginState.LOGIN_WRONG_PASSWORD);
        }
        return LoginServiceRes.getSuccessRes(jwtUtil.generateToken(id));
    }


    /**
     * 注册
     * @param registerUser 注册用户
     * @return LoginServiceRes 包含状态值
     */
    public LoginServiceRes register(LoginUser registerUser) {
        if (!UserValidCheckUtils.checkUser(registerUser)) {
            return LoginServiceRes.getErrorRes(LoginState.INVALID_USER);
        }
        Integer id = userRepository.registerUser(registerUser);
        if (id == null) {
            return LoginServiceRes.getErrorRes(LoginState.REGISTER_ERROR);
        }
        return LoginServiceRes.getSuccessRes(jwtUtil.generateToken(id));
    }
}
