package com.shiaofuk.sqlserver.bean.result;

import lombok.Data;

@Data
public class SuccessResult<T> extends Result<T> {
    public SuccessResult() {
        this.code = 200;
        this.message = "success";
    }

    public SuccessResult(T data) {
        this();
        this.data = data;
    }

    public SuccessResult(String message, T data) {
        this();
        this.message = message;
        this.data = data;
    }

}
