package com.cruz_sur.api.repository;

import com.cruz_sur.api.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository  extends JpaRepository<Boleta, Long> {
}
