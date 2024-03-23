package com.fiap.adm.application.web.exception;

import com.fiap.adm.application.web.RelatorioControllerAdapter;
import com.fiap.adm.domain.exception.EmailNaoInformadoException;
import com.fiap.adm.domain.exception.MesInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(assignableTypes = RelatorioControllerAdapter.class)
public class RelatorioExceptionHandler {
    @ExceptionHandler(MesInvalidoException.class)
    public ResponseEntity<?> senhaFraca(MesInvalidoException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Mês informado inválido", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    @ExceptionHandler(EmailNaoInformadoException.class)
    public ResponseEntity<?> emailNaoInformado(EmailNaoInformadoException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Email não informado ou inválido", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
