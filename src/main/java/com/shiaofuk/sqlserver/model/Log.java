package com.shiaofuk.sqlserver.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @NotNull(message = "不能为null")
    private Integer id;

    @Size(max = 30,message = "最大长度要小于 30")
    private String name;

    private String content;

    public Log(String name, String content) {
        this.name = name;
        this.content = content;
    }
}