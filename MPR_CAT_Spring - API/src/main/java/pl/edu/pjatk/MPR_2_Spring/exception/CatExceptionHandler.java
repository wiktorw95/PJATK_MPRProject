package pl.edu.pjatk.MPR_2_Spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CatExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {CatNotFoundException.class})
    protected ResponseEntity<Object> handleCatNotFoundException(CatNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CatsNotFoundException.class})
    protected ResponseEntity<Object> handleCatsNotFoundException(CatNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CatAlreadyExistException.class})
    protected ResponseEntity<Object> handleCatAlreadyExist(CatAlreadyExistException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(value = {WrongFormatException.class})
    protected ResponseEntity<Object> handleWrongFormatException(WrongFormatException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
