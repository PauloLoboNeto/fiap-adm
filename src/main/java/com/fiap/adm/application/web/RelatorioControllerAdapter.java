package com.fiap.adm.application.web;

import com.fiap.adm.application.web.response.RelatorioResponse;
import com.fiap.adm.domain.exception.EmailNaoInformadoException;
import com.fiap.adm.domain.exception.MesInvalidoException;
import com.fiap.adm.domain.model.Ponto;
import com.fiap.adm.domain.model.Usuario;
import com.fiap.adm.domain.ports.in.IPontoUseCasePort;
import com.fiap.adm.domain.ports.out.IEmailSenderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/adm/relatorios")
@RequiredArgsConstructor
public class RelatorioControllerAdapter {
    private final IPontoUseCasePort pontoUseCasePort;

    @GetMapping(value = "/pontos/{ano}/{mes}", produces = "application/json")
    public ResponseEntity<?> relatorioPorPeriodo(@PathVariable Integer ano, @PathVariable Integer mes) {
        if ((mes > 12) || (mes < 1)) {
            throw new MesInvalidoException();
        }
        LocalDate dataInicial = LocalDate.of(ano, mes, 1);
        LocalDate dataFinal = LocalDate.of(ano, mes, dataInicial.lengthOfMonth());
        List<Ponto> retorno = pontoUseCasePort.buscarPorPeriodo(Date.valueOf(dataInicial), Date.valueOf(dataFinal));

        Map<LocalDate, List<LocalTime>> espelho = Ponto.gerarEspelhoPonto(retorno);
        List<RelatorioResponse> relatorio = new ArrayList<>();
        for (Map.Entry<LocalDate, List<LocalTime>> entry : espelho.entrySet()) {
            RelatorioResponse rel = RelatorioResponse.builder()
                    .dia(entry.getKey())
                    .horarios(entry.getValue())
                    .horasTrabalhadas(Ponto.calcularTempoTrabalhado(entry.getValue()))
                    .build();
            relatorio.add(rel);
        }

        return ResponseEntity.ok(relatorio);
    }

    @PostMapping(value = "/pontos/{ano}/{mes}", produces = "application/json")
    public ResponseEntity<?> relatorioPorPeriodo(@PathVariable Integer ano, @PathVariable Integer mes, @AuthenticationPrincipal Usuario user) throws MessagingException, UnsupportedEncodingException {
        if ((mes > 12) || (mes < 1)) {
            throw new MesInvalidoException();
        }
        if (Objects.isNull(user.getEmail())) {
            throw new EmailNaoInformadoException();
        }

        LocalDate dataInicial = LocalDate.of(ano, mes, 1);
        LocalDate dataFinal = LocalDate.of(ano, mes, dataInicial.lengthOfMonth());
        List<Ponto> pontosRegistradosNoPeriodo = pontoUseCasePort.buscarPorPeriodo(Date.valueOf(dataInicial), Date.valueOf(dataFinal));

        Map<LocalDate, List<LocalTime>> espelho = Ponto.gerarEspelhoPonto(pontosRegistradosNoPeriodo);
        List<RelatorioResponse> relatorio = new ArrayList<>();
        for (Map.Entry<LocalDate, List<LocalTime>> entry : espelho.entrySet()) {
            RelatorioResponse rel = RelatorioResponse.builder()
                    .dia(entry.getKey())
                    .horarios(entry.getValue())
                    .horasTrabalhadas(Ponto.calcularTempoTrabalhado(entry.getValue()))
                    .build();
            relatorio.add(rel);
        }

        pontoUseCasePort.sendEmail(user.getEmail(), "Relatório de Espelho de Ponto", RelatorioResponse.toHtml(relatorio, user));

        return ResponseEntity.ok("Espelho de ponto enviado para o email: "+user.getEmail());
    }

}
