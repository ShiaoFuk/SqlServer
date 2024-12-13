package com.shiaofuk.sqlserver.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blacklist {
    @NotNull(message = "不能为null")
    private Integer id;

    @NotNull(message = "不能为null")
    private Integer userId;

    private Integer badCount;
}