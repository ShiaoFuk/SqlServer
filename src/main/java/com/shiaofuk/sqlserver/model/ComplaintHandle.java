package com.shiaofuk.sqlserver.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintHandle {
    private Integer id;

    private Integer complaintId;

    private Integer managerId;

    private String handleResult;

    private Date createdTime;

    private Date updatedTime;
}