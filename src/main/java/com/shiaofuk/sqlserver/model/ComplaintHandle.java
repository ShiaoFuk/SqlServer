package com.shiaofuk.sqlserver.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintHandle {
    @NotNull(message = "不能为null")
    private Integer id;

    @NotNull(message = "不能为null")
    private Integer complaintId;

    @NotNull(message = "不能为null")
    private Integer managerId;

    @NotBlank(message = "不能为空")
    private String handleResult;
}