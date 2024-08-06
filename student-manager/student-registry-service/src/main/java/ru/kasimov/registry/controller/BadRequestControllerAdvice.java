package ru.kasimov.registry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class BadRequestControllerAdvice {
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleBindException(MethodArgumentNotValidException exception, Locale locale) {
        BindingResult bindingResult = exception.getBindingResult();
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                this.messageSource.getMessage(
                        "errors.400.title", new Object[0], "errors.400.title", locale
                )
        );

        problemDetail.setProperty(
                "errors",
                bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList()
        );

        return ResponseEntity.badRequest().body(problemDetail);
    }
}
