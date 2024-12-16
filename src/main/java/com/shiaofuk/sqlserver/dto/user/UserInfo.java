package com.shiaofuk.sqlserver.dto.user;

import com.shiaofuk.sqlserver.rentation.IDCardValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserInfo {
    @NotNull
    String token;
    @IDCardValidator.IDCard
    String certificateNumber;
    @Email
    String email;
}

