package com.shiaofuk.sqlserver.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form {
    private Integer id;

    private Integer thingId;

    private Integer userId;

    private Integer state;

    private Date createdTime;

    private Date updatedTime;
}