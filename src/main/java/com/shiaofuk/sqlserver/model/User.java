package com.shiaofuk.sqlserver.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull(message = "不能为null")
    private Integer id;

    @Size(max = 20, message = "最大长度要小于 20")
    private String certificateNumber;

    @Size(max = 20, message = "最大长度要小于 20")
    @NotBlank(message = "不能为空")
    private String username;

    @Size(max = 30, message = "最大长度要小于 30")
    private String email;

    @Size(max = 100, message = "最大长度要小于 100")
    @NotBlank(message = "不能为空")
    private String password;

    @NotNull(message = "不能为null")
    private Integer permission;

    @NotNull(message = "不能为null")
    private Integer state;
}