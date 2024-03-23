package com.fiap.adm.domain.model;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Ponto {
    private UUID id;
    private String usuario;
    private String matricula;
    private String email;
    private Date data;

    public static Map<LocalDate, List<LocalTime>> gerarEspelhoPonto(List<Ponto> registrosPonto) {
        Map<LocalDate, List<LocalTime>> espelhoPonto = new HashMap<>();

        for (Ponto registro : registrosPonto) {
            LocalDate data = registro.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime hora = registro.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

            if (!espelhoPonto.containsKey(data)) {
                espelhoPonto.put(data, new ArrayList<>());
            }

            espelhoPonto.get(data).add(hora);
        }

        return espelhoPonto;
    }

    public static Duration calcularTempoTrabalhado(List<LocalTime> horarios) {
        Duration tempoTrabalhado = Duration.ZERO;

        if (horarios.size() % 2 == 0) { // Deve haver um número par de registros
            for (int i = 0; i < horarios.size(); i += 2) {
                LocalTime entrada = horarios.get(i);
                LocalTime saida = horarios.get(i + 1);

                tempoTrabalhado = tempoTrabalhado.plus(Duration.between(entrada, saida));
            }
        } else {
            System.out.println("Número ímpar de registros para o dia " + horarios);
        }

        return tempoTrabalhado;
    }
}
