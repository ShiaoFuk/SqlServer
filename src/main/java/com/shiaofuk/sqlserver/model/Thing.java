package com.shiaofuk.sqlserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thing {
    @NotNull(message = "不能为null")
    private Integer id;

    @Size(max = 20, message = "最大长度要小于 20")
    @NotBlank(message = "不能为空")
    private String name;

    private String description;

    @Size(max = 50, message = "最大长度要小于 50")
    @NotBlank(message = "不能为空")
    private String place;

    @NotNull(message = "不能为null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")  // 指定日期格式
    private Date time;
}