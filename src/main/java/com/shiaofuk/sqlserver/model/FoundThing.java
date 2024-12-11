package com.shiaofuk.sqlserver.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoundThing {
    private Integer id;

    private Integer thingId;

    private Date createdTime;

    private Date updatedTime;
}