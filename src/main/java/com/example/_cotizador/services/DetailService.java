package com.example._cotizador.services;

import com.example._cotizador.dto.DetallePlanDto;
import com.example._cotizador.entity.DetallePlan;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DetailService {

    DetallePlan saveDetallePlan(DetallePlanDto dto);

    Optional<DetallePlan> getDetallePlanById(Long id);

    Optional<DetallePlan> getByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<DetallePlan> getAllDetallePlans();

    void deleteDetallePlan(Long id);

    @Transactional
    @org.springframework.transaction.annotation.Transactional
    List<DetallePlan> saveDetallePlansMasivo(List<DetallePlanDto> dtos);
}

