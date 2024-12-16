package com.shiaofuk.sqlserver.bean;

import lombok.Getter;

@Getter
public enum FormState {
    UNSOLVED(0),
    DELETE(1),
    SOLVED(2);


    final int val;

    FormState(int val) {
        this.val = val;
    }

}
