package com.example._cotizador.services;

import com.example._cotizador.dto.DetallePlanDto;
import com.example._cotizador.entity.DetallePlan;
import org.springframework.stereotype.Service;
import com.example._cotizador.repository.DetallePlanRepository;
import com.example._cotizador.services.builder.DetallePlanBuilder;

import java.util.List;
import java.util.Optional;


@Service
public class DetailServiceImpl implements DetailService {

    private final DetallePlanRepository repository;
    private final DetallePlanBuilder builder;

    public DetailServiceImpl(DetallePlanRepository repository, DetallePlanBuilder builder) {
        this.repository = repository;
        this.builder = builder;
    }

    @Override
    public DetallePlan saveDetallePlan(DetallePlanDto dto) {
        // Usamos el builder para transformar el DTO en entidad
        DetallePlan entity = builder.build(dto);
        // Guardamos en la base de datos
        return repository.save(entity);
    }

    @Override
    public Optional<DetallePlan> getDetallePlanById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<DetallePlan> getAllDetallePlans() {
        return List.of();
    }

    @Override
    public void deleteDetallePlan(Long id) {

    }
}
