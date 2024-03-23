package com.fiap.adm.application.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fiap.adm.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RelatorioResponse {
    private LocalDate dia;
    private List<LocalTime> horarios;
    @JsonSerialize(using = DurationSerializer.class)
    private Duration horasTrabalhadas;

    public static String toHtml(List<RelatorioResponse> relatorio, Usuario user) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div>");
        sb.append("<h1>Espelho de Ponto "+user.getNome()+"</h1>");
        relatorio.forEach(e -> {
            long hours = e.getHorasTrabalhadas().toHours();
            long minutes = e.getHorasTrabalhadas().toMinutesPart();
            long seconds = e.getHorasTrabalhadas().toSecondsPart();

            String formattedDuration = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            sb.append("<h3>"+e.getDia().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" - "+formattedDuration+"</h3>");
            e.getHorarios().forEach(x -> {
                sb.append("<span>"+x.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"</span><br>");
            });
        });
        sb.append("</div>");
        return sb.toString();
    }
}
