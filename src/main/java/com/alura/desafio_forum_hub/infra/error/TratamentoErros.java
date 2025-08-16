package com.alura.desafio_forum_hub.infra.error;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratamentoErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<?>> error400Handler(MethodArgumentNotValidException e) {
        var erros = e.getFieldErrors().stream().map(ValidacaoErrosDados::new).toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> error404Handler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IntegrityValidation.class)
    public ResponseEntity<String> errorHandlerValidacaoDeIntegridade(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> errorHandlerValidacaoDeNegocio(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> invalidBodyHandler(Exception e) {
        return ResponseEntity.badRequest().body("Algumas partes do corpo da solicitação estão expressas incorretamente.");
    }

    private record ValidacaoErrosDados(String campo, String erro) {
        public ValidacaoErrosDados(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
