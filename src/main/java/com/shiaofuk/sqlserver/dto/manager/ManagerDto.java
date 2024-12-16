package com.shiaofuk.sqlserver.dto.manager;

import com.shiaofuk.sqlserver.bean.UserPermission;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ManagerDto {
    @NotNull
    Integer userId;
}
