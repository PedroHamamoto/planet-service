package br.com.hamamoto.planetservice.infrastructure.exception.domain;

import org.springframework.http.HttpStatus;

public enum Message {

    UNKNOWN_ORIGIN("Origin should be 'database' or 'swapi' ", HttpStatus.BAD_REQUEST, 400001),

    PLANET_NOT_FOUND("Planet not found", HttpStatus.NOT_FOUND, 404001);

    private final HttpStatus status;
    private final String message;
    private final int code;

    Message(String message, HttpStatus status, int code) {
        this.message = message;
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}
