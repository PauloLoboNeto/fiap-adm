package com.fiap.adm.infrastructure.persistence.repository;

import com.fiap.adm.infrastructure.persistence.entity.PontoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PontoRepository extends JpaRepository<PontoEntity, UUID> {
    List<PontoEntity> findPontoEntityByUsuarioEquals(String usuario);
    List<PontoEntity> findPontoEntityByDataBetween(Date inicio, Date fim);
}
