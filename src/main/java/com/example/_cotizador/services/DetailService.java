package com.example._cotizador.services;

import com.example._cotizador.dto.DetallePlanDto;
import com.example._cotizador.entity.DetallePlan;

import java.util.List;
import java.util.Optional;


public interface DetailService {
    DetallePlan saveDetallePlan(DetallePlanDto dto);

    Optional<DetallePlan> getDetallePlanById(Long id);

    List<DetallePlan> getAllDetallePlans();

    void deleteDetallePlan(Long id);
}
