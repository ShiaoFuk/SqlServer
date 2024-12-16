package com.shiaofuk.sqlserver.dto.form;

import com.shiaofuk.sqlserver.bean.FormState;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormDto {
    @NotNull(message = "不能为null")
    private Integer thingId;

    @NotNull(message = "不能为null")
    private FormState state;
}
