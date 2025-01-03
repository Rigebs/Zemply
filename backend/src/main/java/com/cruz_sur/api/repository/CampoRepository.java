package com.cruz_sur.api.repository;

import com.cruz_sur.api.model.Campo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampoRepository extends JpaRepository<Campo, Long> {
    List<Campo> findByUsuario_IdAndUsuario_SedeIsNotNullAndEstado(Long usuarioId, Character estado);

    List<Campo> findByUsuario_IdAndUsuario_SedeIsNotNull(Long usuarioId);

}
