package com.shiaofuk.sqlserver.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Component;

@Component
public class Argon2PasswordHasher {
    private final static Integer ITERATION = 3;  // 迭代次数，越大则迭代事件越长
    private final static Integer MEMORY = 65536;  // 内存消耗 KB
    private final static Integer PARALLELISM = 1;  // 并发线程数


    // 哈希密码
    public String hashPassword(String password) {
        // 创建 Argon2 实例
        Argon2 argon2 = Argon2Factory.create();
        // 生成哈希
        try {
            return argon2.hash(ITERATION, MEMORY, PARALLELISM, password);
        } finally {
            // 确保密码不会被保留在内存中
            argon2.wipeArray(password.toCharArray());
        }
    }

    // 验证密码
    public boolean verifyPassword(String hash, String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(hash, password);
    }

}
