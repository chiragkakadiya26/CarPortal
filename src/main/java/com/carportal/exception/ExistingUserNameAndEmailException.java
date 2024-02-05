package com.carportal.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ExistingUserNameAndEmailException extends RuntimeException {

    private String error;
    public ExistingUserNameAndEmailException(String error) {

        this.error = error;
    }
}
