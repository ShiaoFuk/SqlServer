package com.shiaofuk.sqlserver.dto.thing;

import com.shiaofuk.sqlserver.bean.ThingState;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ThingPageInfo {
    @DecimalMin(value = "0", message = "页码从0开始")
    Integer page;
    @DecimalMin(value = "1", message = "页大小至少为1")
    Integer pageSize;
    @NotNull
    ThingState thingState;
}
