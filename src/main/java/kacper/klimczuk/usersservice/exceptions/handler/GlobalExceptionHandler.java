package kacper.klimczuk.usersservice.exceptions.handler;

import kacper.klimczuk.usersservice.exceptions.UserNotFoundException;
import kacper.klimczuk.usersservice.exceptions.errors.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }
}
