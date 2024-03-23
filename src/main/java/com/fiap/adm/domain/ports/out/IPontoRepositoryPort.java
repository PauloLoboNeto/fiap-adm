package com.fiap.adm.domain.ports.out;

import com.fiap.adm.domain.model.Ponto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPontoRepositoryPort {
    Optional<Ponto> salvar(Ponto ponto);
    List<Ponto> buscarPorPeriodo(Date inicio, Date fim);
}
