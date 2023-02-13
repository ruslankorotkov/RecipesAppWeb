package sky.pro.recipesappweb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sky.pro.recipesappweb.exception.ValidationException;

@ControllerAdvice
public class ApiExceptionController {
    @ExceptionHandler
    public ResponseEntity<String> handlerValidationException(ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
