package com.shiaofuk.sqlserver.bean;

import lombok.Getter;

@Getter
public enum ComplaintState {
    UNSOLVED(0),
    DELETE(1),
    SOLVED(2);

    final int val;
    ComplaintState(int val) {
        this.val = val;
    }
}
