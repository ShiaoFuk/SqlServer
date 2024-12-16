package com.shiaofuk.sqlserver.dto.complaint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ComplaintHandleDto {
    @NotNull
    Integer complaintId;
    @NotBlank
    String handleResult;
}
