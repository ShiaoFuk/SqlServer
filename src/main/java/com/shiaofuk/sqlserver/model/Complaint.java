package com.shiaofuk.sqlserver.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {
    private Integer id;

    private Integer userId;

    private Integer formId;

    private Integer type;

    private String reason;

    private Integer state;

    private Date createdTime;

    private Date updatedTime;
}