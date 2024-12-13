package com.shiaofuk.sqlserver.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorTaker {
    @NotNull(message = "不能为null")
    private Integer id;

    @NotNull(message = "不能为null")
    private Integer userId;

    @NotNull(message = "不能为null")
    private Integer formId;
}