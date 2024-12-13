package com.shiaofuk.sqlserver.bean.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ErrorResult<T> extends Result<T> {
    public ErrorResult() {
        this.code = 500;
        this.message = "error";
    }

    public ErrorResult(String message) {
        this();
        this.message = message;
    }

    public ErrorResult(String message, T data) {
        this();
        this.message = message;
        this.data = data;
    }
}