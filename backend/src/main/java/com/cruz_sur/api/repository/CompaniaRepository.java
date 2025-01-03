package com.cruz_sur.api.repository;

import com.cruz_sur.api.model.Compania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompaniaRepository extends JpaRepository<Compania, Long> {
    List<Compania> findByEstado(Character estado);

}
