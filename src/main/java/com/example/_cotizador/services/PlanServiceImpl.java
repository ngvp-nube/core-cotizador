package com.example._cotizador.services;

import com.example._cotizador.dto.PlanComercialDto;
import com.example._cotizador.entity.PlanComercial;
import com.example._cotizador.repository.PlanComercialRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    public List<PlanComercial> getAllPlanes() {
        return planRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Map<String, Object> getPlanesPaginados(int page, int size) {
        // Obtener todos los planes ordenados
        List<PlanComercial> todosLosPlanes = planRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        
        // Calcular total de elementos
        int totalElements = todosLosPlanes.size();
        
        // Calcular índices para paginación
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        
        // Validar que la página solicitada exista
        if (startIndex >= totalElements && totalElements > 0) {
            throw new IllegalArgumentException("La página solicitada no existe");
        }
        
        // Obtener sublista para la página actual
        List<PlanComercial> planesPagina = todosLosPlanes.subList(startIndex, endIndex);
        
        // Calcular total de páginas
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        // Construir respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("data", planesPagina);
        response.put("currentPage", page);
        response.put("pageSize", size);
        response.put("totalElements", totalElements);
        response.put("totalPages", totalPages);
        response.put("first", page == 0);
        response.put("last", page >= totalPages - 1);
        
        return response;
    }

    /**
    @Override
    public List<PlanComercial> buscarPlanes(String termino) {
        return planRepository.findAll();
    }**/


    @Override
    public PlanComercial savePlan(PlanComercialDto dto) {
        PlanComercial nuevo = new PlanComercial();
        // Valores iniciales
        nuevo.setPrestadores(new ArrayList<>());
        nuevo.setVisible(true);


        actualizarDatosEntidad(dto, nuevo);

        return planRepository.save(nuevo);
    }


    @Override
    public PlanComercial updatePlan(Long id, PlanComercialDto dto) {

        PlanComercial existente = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado con ID: " + id));


        actualizarDatosEntidad(dto, existente);

        return planRepository.save(existente);
    }

    @Override
    public void deletePlan(Long id) {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar, el plan no existe.");
        }
    }

    @Override
    public PlanComercial cambiarVisibilidad(Long id) {
        PlanComercial plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        plan.setVisible(plan.getVisible() == null || !plan.getVisible());
        return planRepository.save(plan);
    }

    @Override
    public Map<String, Object> cargaMasiva(List<PlanComercialDto> planesDto) {
        Map<String, Object> response = new HashMap<>();
        int guardados = 0;
        for (PlanComercialDto dto : planesDto) {
            savePlan(dto); // Reutilizamos savePlan
            guardados++;
        }
        response.put("mensaje", "Se cargaron " + guardados + " planes.");
        return response;
    }

    private void actualizarDatosEntidad(PlanComercialDto dto, PlanComercial entidad) {
        // 1. Campos básicos
        entidad.setCodigoPlan(dto.getCodigoPlan());
        entidad.setNombrePlan(dto.getNombrePlan()); // Isapre
        entidad.setPlan(dto.getPlan());             // Nombre comercial
        entidad.setPrecioBase(dto.getPrecioBase());
        entidad.setTopeAnualUf(dto.getTopeAnualUf());
        entidad.setHospitalaria(dto.getHospitalaria());
        entidad.setAmbulatoria(dto.getAmbulatoria());
        entidad.setUrgencia(dto.getUrgencia());


        if (dto.getPreferente() != null) {
            entidad.setPreferente(dto.getPreferente());
        }


        if (dto.getLogo() != null && !dto.getLogo().isEmpty()) {
            entidad.setLogo(dto.getLogo());
        } else {

            entidad.setLogo(obtenerRutaLogo(dto.getNombrePlan()));
        }
    }

    private String obtenerRutaLogo(String nombreIsapre) {
        if (nombreIsapre == null) return "/default.png";
        String key = nombreIsapre.toLowerCase().trim();

        if (key.contains("consalud")) return "/consalud.png";
        if (key.contains("colmena")) return "/colmena.png";
        if (key.contains("banmedica")) return "/banmedica.png";
        if (key.contains("cruz blanca") || key.contains("cruzblanca")) return "/cruzblanca.png";
        if (key.contains("nueva masvida") || key.contains("masvida")) return "/nuevamasvida.png";
        if (key.contains("vida tres") || key.contains("vidatres")) return "/vidatres.png";

        return "/default.png";
    }
}