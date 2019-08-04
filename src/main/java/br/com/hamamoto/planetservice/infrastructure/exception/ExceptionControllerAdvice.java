package br.com.hamamoto.planetservice.infrastructure.exception;

import br.com.hamamoto.planetservice.infrastructure.exception.domain.ApplicationException;
import br.com.hamamoto.planetservice.infrastructure.exception.domain.ErrorResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpEntity<Map<String, Object>> handleException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return ResponseEntity.badRequest().body(body);

    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResource> handleException(ApplicationException e) {
        ErrorResource errorResource = new ErrorResource(e.getCode(), e.getMessage());
        return new ResponseEntity<>(errorResource, e.getStatus());
    }
}
