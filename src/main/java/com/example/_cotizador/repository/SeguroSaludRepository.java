package com.example._cotizador.repository;

import com.example._cotizador.entity.SeguroSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeguroSaludRepository extends JpaRepository<SeguroSalud, Long> {
    Optional<SeguroSalud> findByCodigoPlan(String codigoPlan);
    boolean existsByCodigoPlan(String codigoPlan);
}
