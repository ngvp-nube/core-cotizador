package com.example._cotizador.services;

import com.example._cotizador.dto.PlanComercialDto;
import com.example._cotizador.entity.PlanComercial;
import com.example._cotizador.repository.PlanComercialRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap; // Importante para el Map de respuesta
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanComercialRepository planRepository;

    public PlanServiceImpl(PlanComercialRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public Map<String, Object> cargaMasiva(List<PlanComercialDto> planesDto) {
        Map<String, Object> response = new HashMap<>();
        int nuevos = 0;
        int actualizados = 0;
        int errores = 0;

        for (PlanComercialDto dto : planesDto) {
            try {
                if (dto.getCodigoPlan() == null || dto.getCodigoPlan().trim().isEmpty()) {
                    continue;
                }

                PlanComercial entidad = mapDtoToEntity(dto);
                List<PlanComercial> existentes = planRepository.findByCodigoPlan(entidad.getCodigoPlan());

                if (existentes != null && !existentes.isEmpty()) {
                    // UPDATE
                    PlanComercial existente = existentes.get(0);
                    entidad.setId(existente.getId());
                    actualizados++;
                } else {
                    // INSERT
                    nuevos++;
                }
                planRepository.save(entidad);

            } catch (Exception e) {
                errores++;
                System.err.println("❌ Error procesando plan " + dto.getCodigoPlan() + ": " + e.getMessage());
            }
        }

        response.put("mensaje", "✅ Proceso finalizado: " + nuevos + " nuevos planes agregados, " + actualizados + " actualizados.");
        response.put("status", "ok");
        return response;
    }

    @Override
    public List<PlanComercial> getAllPlanes() {
        return planRepository.findAll();
    }

    @Override
    public PlanComercial savePlan(PlanComercialDto dto) {
        PlanComercial entidad = mapDtoToEntity(dto);
        List<PlanComercial> existentes = planRepository.findByCodigoPlan(entidad.getCodigoPlan());

        if (existentes != null && !existentes.isEmpty()) {
            throw new RuntimeException("El código " + entidad.getCodigoPlan() + " ya existe.");
        }
        return planRepository.save(entidad);
    }

    // --- Mover el método helper aquí y hacerlo private ---
    private PlanComercial mapDtoToEntity(PlanComercialDto dto) {
        PlanComercial p = new PlanComercial();
        p.setCodigoPlan(dto.getCodigoPlan());
        p.setNombrePlan(dto.getNombrePlan());
        p.setPlan(dto.getPlan());
        p.setPrecioBase(dto.getPrecioBase());
        p.setTopeAnualUf(dto.getTopeAnualUf());
        p.setHospitalaria(dto.getHospitalaria());
        p.setAmbulatoria(dto.getAmbulatoria());
        p.setUrgencia(dto.getUrgencia());
        p.setPuntaje(dto.getPuntaje());
        // p.setPreferente(dto.getPreferente()); // Descomentar si agregas el campo al DTO

        if (dto.getPrestadores() != null) {
            p.setPrestadores(dto.getPrestadores());
        } else {
            p.setPrestadores(new ArrayList<>());
        }
        return p;
    }
}