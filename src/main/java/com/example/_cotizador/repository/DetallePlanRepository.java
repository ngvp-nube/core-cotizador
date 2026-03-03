package com.example._cotizador.repository;

import com.example._cotizador.entity.DetallePlan;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePlanRepository extends JpaRepository<DetallePlan, Long> {

    Optional<DetallePlan> findByCodigo(String codigo);
    List<DetallePlan> findAllByOrderByIdAsc();

    boolean existsByCodigo(String codigo);
}

