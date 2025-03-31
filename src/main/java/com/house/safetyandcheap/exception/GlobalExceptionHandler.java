package com.house.safetyandcheap.exception;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorObject> handleNotFoundException(
            final @NonNull EntityNotFoundException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }


    /**
     * Стандартный обработчик исключений.
     *
     * @param e ошибка
     * @return Ответ с сообщением об ошибке и статусом 500 Internal Server Error
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorObject> handleRunTimeException(
            final @NonNull RuntimeException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Стандартный обработчик исключений.
     *
     * @param e e исключение типа BadRequestException
     * @return Ответ с сообщением об ошибке и статусом 400
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorObject> handleBadRequestException(
            final @NonNull BadRequestException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработчик исключений типа MethodArgumentNotValidException.
     *
     * @param e бработчик исключений типа MethodArgumentTypeMismatchException.
     * @return Ответ с сообщением об ошибке и статусом 400
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject>
    handleMethodArgumentTypeMismatchException(
            final @NonNull MethodArgumentTypeMismatchException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработчик исключений типа MethodArgumentNotValidException.
     *
     * @param e бработчик исключений типа MethodArgumentTypeMismatchException.
     * @return Ответ с сообщением об ошибке и статусом 400
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorObject>
    handleMissingServletRequestParameterException(
            final @NonNull MissingServletRequestParameterException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработчик исключений типа HttpClientErrorException.
     *
     * @param e бработчик исключений типа HttpClientErrorException.
     * @return Ответ с сообщением об ошибке и статусом 400
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorObject>
    handleHttpClientErrorException(
            final @NonNull HttpClientErrorException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработчик исключений типа DataIntegrityViolationException.
     *
     * @param e бработчик исключений типа DataIntegrityViolationException.
     * @return Ответ с сообщением об ошибке и статусом 400
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorObject>
    handleDataIntegrityViolationException(
            final @NonNull DataIntegrityViolationException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject,
                HttpStatus.BAD_REQUEST);
    }
    /**
     * Обработчик исключений типа DataIntegrityViolationException.
     *
     * @param e бработчик исключений типа DataIntegrityViolationException.
     * @return Ответ с сообщением об ошибке и статусом 400
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorObject>
    handleNoResourceFoundException(
            final @NonNull NoResourceFoundException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(e.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject,
                HttpStatus.BAD_REQUEST);
    }
}
