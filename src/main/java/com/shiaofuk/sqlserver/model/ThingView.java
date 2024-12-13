package com.shiaofuk.sqlserver.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThingView {
    @NotNull(message = "不能为null")
    private Integer id;

    @Size(max = 20, message = "最大长度要小于 20")
    @NotBlank(message = "不能为空")
    private String name;

    @Size(max = 50, message = "最大长度要小于 50")
    @NotBlank(message = "不能为空")
    private String place;

    @NotNull(message = "不能为null")
    private Date time;
}