package org.venti.common.constant;

import lombok.Getter;

public enum ValidStatus {

    INVALID(0),

    VALID(1);

    @Getter
    private final int code;

    ValidStatus(int code) {
        this.code = code;
    }

    public static ValidStatus fromCode(int code) {
        for (ValidStatus status : ValidStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return INVALID;
    }

}
