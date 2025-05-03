package org.example.hrm.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class CoreException extends RuntimeException {

    private String message;

    private HttpStatus status;

  public CoreException(CoreErrorCode code) {
    super();
    this.message = code.message();
    this.status = code.status();
  }
}
