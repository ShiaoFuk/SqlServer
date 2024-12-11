package com.shiaofuk.sqlserver.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thing {
    private Integer id;

    private String name;

    private String description;

    private String place;

    private Date time;

    private Date createdTime;

    private Date updatedTime;
}