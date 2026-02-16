package com.example._cotizador.services;

import com.example._cotizador.dto.DetallePlanDto;
import com.example._cotizador.entity.DetallePlan;
import com.example._cotizador.entity.PlanComercial;
import com.example._cotizador.repository.DetallePlanRepository;
import com.example._cotizador.services.builder.DetallePlanBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetailServiceImpl implements DetailService {

    private final DetallePlanRepository repository;
    private final DetallePlanBuilder builder;

    public DetailServiceImpl(DetallePlanRepository repository, DetallePlanBuilder builder) {
        this.repository = repository;
        this.builder = builder;
    }

    @Override
    public DetallePlan saveDetallePlan(DetallePlanDto dto) {
        DetallePlan incoming = builder.buildFromDto(dto);
        Optional<DetallePlan> existingOpt = repository.findByCodigo(incoming.getCodigo());

        if (existingOpt.isPresent()) {
            DetallePlan existing = existingOpt.get();
            existing.setPdfData(incoming.getPdfData());
            existing.setFileName(incoming.getFileName());
            existing.setContentType(incoming.getContentType());
            return repository.save(existing);
        }
        return repository.save(incoming);
    }

    @Override
    public Optional<DetallePlan> getDetallePlanById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<DetallePlan> getByCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) return Optional.empty();
        return repository.findByCodigo(codigo.trim());
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) return false;
        return repository.existsByCodigo(codigo.trim());
    }

    @Override
    public List<DetallePlan> getAllDetallePlans() {
        return repository.findAllByOrderByIdAsc();
    }

    @Override
    public void deleteDetallePlan(Long id) {
        if (id != null && repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    @Override
    public List<DetallePlan> saveDetallePlansMasivo(List<DetallePlanDto> dtos) {
        List<DetallePlan> listaGuardada = new ArrayList<>();
        for (DetallePlanDto dto : dtos) {
            listaGuardada.add(saveDetallePlan(dto));
        }
        return listaGuardada;
    }

}