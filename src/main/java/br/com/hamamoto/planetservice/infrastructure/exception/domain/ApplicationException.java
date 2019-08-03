package br.com.hamamoto.planetservice.infrastructure.exception.domain;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private final HttpStatus status;
    private final int code;

    public ApplicationException(Message message) {
        super(message.getMessage());
        this.code = message.getCode();
        this.status = message.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }
}
