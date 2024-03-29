package com.carportal.exception;

import com.carportal.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception){

        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setError("Not Found ");
        response.setMessage(exception.getMessage());

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistingUserNameAndEmailException.class)
    public ResponseEntity<Object> handelUserEmailAndUserNameExist(ExistingUserNameAndEmailException exception){
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setError("Duplicate Email Error");
        response.setMessage(exception.getError());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleMethodArgsNotValidException(MethodArgumentNotValidException exception){

        Map<String, String> map = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) ->{

            String filedName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            map.put(filedName, message);
        });

        return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<Object> handelPasswordException(PasswordException exception){

        String errorMessage = exception.getMessage();
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }
}
