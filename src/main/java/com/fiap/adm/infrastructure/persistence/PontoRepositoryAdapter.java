package com.fiap.adm.infrastructure.persistence;


import com.fiap.adm.domain.model.Ponto;
import com.fiap.adm.domain.ports.out.IPontoRepositoryPort;
import com.fiap.adm.infrastructure.persistence.entity.PontoEntity;
import com.fiap.adm.infrastructure.persistence.repository.PontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PontoRepositoryAdapter implements IPontoRepositoryPort {
    private final PontoRepository repository;

    @Override
    public Optional<Ponto> salvar(Ponto ponto) {
        PontoEntity pontoEntity = new PontoEntity().from(ponto);
        return Optional.of(new PontoEntity().to(repository.save(pontoEntity)));
    }

    @Override
    public List<Ponto> buscarPorPeriodo(Date inicio, Date fim) {
        return repository.findPontoEntityByDataBetween(inicio, fim).stream().map(e -> new PontoEntity().to(e)).collect(Collectors.toList());
    }

}
