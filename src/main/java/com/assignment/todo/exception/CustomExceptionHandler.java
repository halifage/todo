package com.assignment.todo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.assignment.todo.constants.Constants.AUTHENTICATION_ERROR_MESSAGE;
import static com.assignment.todo.constants.Constants.USER_ID;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        boolean isMissingUserId = fieldErrors.stream().anyMatch(fieldError -> fieldError.getField().equalsIgnoreCase(USER_ID));
        HttpStatus httpStatus = isMissingUserId ? HttpStatus.UNAUTHORIZED : status;
        String errorMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(". "));
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now(), httpStatus, errorMessage);
        return this.handleExceptionInternal(ex, exceptionDto, headers, httpStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String missingParameterName = ex.getParameterName();
        String errorMessage = missingParameterName + " is missing";
        HttpStatus httpStatus = status;

        if ("userId".equalsIgnoreCase(missingParameterName)) {
            errorMessage = AUTHENTICATION_ERROR_MESSAGE;
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now(), httpStatus, errorMessage);
        return this.handleExceptionInternal(ex, exceptionDto, headers, httpStatus, request);
    }

}
