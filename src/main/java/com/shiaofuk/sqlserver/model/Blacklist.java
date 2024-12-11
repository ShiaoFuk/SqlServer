package com.shiaofuk.sqlserver.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blacklist {
    private Integer id;

    private Integer userId;

    private Integer badCount;

    private Date createdTime;

    private Date updatedTime;
}