package com.shiaofuk.sqlserver.repository;

import com.shiaofuk.sqlserver.dto.user.LoginUser;
import com.shiaofuk.sqlserver.dto.user.UserInfo;
import com.shiaofuk.sqlserver.mapper.UserMapper;
import com.shiaofuk.sqlserver.model.User;
import com.shiaofuk.sqlserver.utils.Argon2PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepository {
    UserMapper userMapper;
    Argon2PasswordHasher argon2PasswordHasher;

    @Autowired
    public UserRepository(UserMapper userMapper, Argon2PasswordHasher argon2PasswordHasher) {
        this.userMapper = userMapper;
        this.argon2PasswordHasher = argon2PasswordHasher;
    }


    /**
     * 登录时判断账号密码是否正确
     *
     * @param loginUser 登录用户账号密码
     * @return 正确则返回id，否则返回null
     */
    public Integer verifyPassword(LoginUser loginUser) {
        User tempUser = userMapper.selectOneIdAndPasswordByUsername(loginUser.getUsername());
        if (argon2PasswordHasher.verifyPassword(tempUser.getPassword(), loginUser.getPassword())) {
            return tempUser.getId();
        }
        return null;
    }

    /**
     * 注册用户
     *
     * @param loginUser 注册账号密码
     * @return 注册成功返回id，否则返回null
     */
    public Integer registerUser(LoginUser loginUser) {
        User user = new User();
        user.setUsername(loginUser.getUsername());
        user.setPassword(argon2PasswordHasher.hashPassword(loginUser.getPassword()));
        userMapper.insertSelective(user);
        return user.getId();
    }


    public boolean updateUserInfo(UserInfo userInfo, int id) {
        User user = new User();
        user.setId(id);
        user.setCertificateNumber(userInfo.getCertificateNumber());
        user.setEmail(userInfo.getEmail());
        // 更新成功
        if (userMapper.updateByPrimaryKeySelective(user) > 0) {
            return true;
        }
        return false;
    }


}
