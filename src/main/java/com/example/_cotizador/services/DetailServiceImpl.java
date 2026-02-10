package com.example._cotizador.services;

import com.example._cotizador.dto.DetallePlanDto;
import com.example._cotizador.entity.DetallePlan;
import com.example._cotizador.repository.DetallePlanRepository;
import com.example._cotizador.services.builder.DetallePlanBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    /**
     * Crea o actualiza un DetallePlan por codigo.
     * - Si existe el codigo, actualiza pdfData, fileName, contentType
     * - Si no existe, lo crea
     */
    @Override
    public DetallePlan saveDetallePlan(DetallePlanDto dto) {
        // Builder valida y convierte Base64 a bytes
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
    @Transactional
    public Optional<DetallePlan> getDetallePlanById(Long id) {
        return repository.findById(id);
    }

    public Optional<DetallePlan> getByCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) return Optional.empty();
        return repository.findByCodigo(codigo.trim());
    }

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
        if (id == null) return;
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }


    @org.springframework.transaction.annotation.Transactional
    @Override
    public List<DetallePlan> saveDetallePlansMasivo(List<DetallePlanDto> dtos) {
        List<DetallePlan> listaGuardada = new java.util.ArrayList<>();

        for (DetallePlanDto dto : dtos) {
            // 1. Convertimos DTO a Entidad (Reusando tu Builder)
            DetallePlan incoming = builder.buildFromDto(dto);

            // 2. Buscamos si ya existe por código
            Optional<DetallePlan> existingOpt = repository.findByCodigo(incoming.getCodigo());

            if (existingOpt.isPresent()) {
                // UPDATE: Actualizamos el existente
                DetallePlan existing = existingOpt.get();
                existing.setPdfData(incoming.getPdfData());
                existing.setFileName(incoming.getFileName());
                existing.setContentType(incoming.getContentType());
                listaGuardada.add(repository.save(existing));
            } else {
                // CREATE: Guardamos uno nuevo
                listaGuardada.add(repository.save(incoming));
            }
        }
        return listaGuardada;
    }
}
