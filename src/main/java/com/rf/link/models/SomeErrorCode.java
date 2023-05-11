package com.rf.link.models;

import com.github.seanyinx.wing.core.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public enum SomeErrorCode implements ErrorCode {
  RESOURCE_NOT_FOUND(1, "resource not found")
  ;

  private final int code;
  private final String cause;

  SomeErrorCode(int code, String cause) {
    this.code = code;
    this.cause = cause;
  }
}
