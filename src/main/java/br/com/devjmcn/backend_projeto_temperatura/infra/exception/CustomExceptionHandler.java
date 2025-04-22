package br.com.devjmcn.backend_projeto_temperatura.infra.exception;

import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.IncorrectPasswordException;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.NoDataFoundException;
import br.com.devjmcn.backend_projeto_temperatura.infra.exception.custom.NotUserFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> generalErrorHandler(Exception e){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorResponse> noDataFoundExceptionHandler(NoDataFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "UUID inválido");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotUserFoundException.class)
    public ResponseEntity<ErrorResponse> handlerNotUserFoundException(NotUserFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> handlerIncorrectPasswordException(IncorrectPasswordException e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
