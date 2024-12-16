package com.shiaofuk.sqlserver.dto.form;

import com.shiaofuk.sqlserver.bean.FormState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateFormDto {
    @NotNull(message = "不能为null")
    private Integer id;

    /**
     * 必须是foundThing里面UNSOLVED的thingID
     */
    @NotNull(message = "不能为null")
    private Integer thingId;

}
