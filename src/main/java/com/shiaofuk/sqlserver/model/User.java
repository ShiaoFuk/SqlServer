package com.shiaofuk.sqlserver.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    private String certificateNumber;

    private String username;

    private String email;

    private String password;

    private Integer permission;

    private Integer state;

    private Date createdTime;

    private Date updatedTime;
}