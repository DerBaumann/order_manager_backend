package ch.wegmann.pierre.order_manager.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @Data
    @AllArgsConstructor
    public static class Error {
        private String error;
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Error> handleNotFound(EntityNotFoundException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
