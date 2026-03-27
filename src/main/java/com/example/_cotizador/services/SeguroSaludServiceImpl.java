package com.example._cotizador.services;

import com.example._cotizador.dto.SeguroSaludDto;
import com.example._cotizador.entity.SeguroSalud;
import com.example._cotizador.repository.SeguroSaludRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SeguroSaludServiceImpl implements SeguroSaludService {

    private final SeguroSaludRepository seguroRepository;

    public SeguroSaludServiceImpl(SeguroSaludRepository seguroRepository) {
        this.seguroRepository = seguroRepository;
    }

    @Override
    public List<SeguroSalud> getAllSeguros() {
        return seguroRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Map<String, Object> getSegurosPaginados(int page, int size) {
        // Obtener todos los seguros ordenados
        List<SeguroSalud> todosLosSeguros = seguroRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        
        // Calcular total de elementos
        int totalElements = todosLosSeguros.size();
        
        // Calcular índices para paginación
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        
        // Validar que la página solicitada exista
        if (startIndex >= totalElements && totalElements > 0) {
            throw new IllegalArgumentException("La página solicitada no existe");
        }
        
        // Obtener sublista para la página actual
        List<SeguroSalud> segurosPagina = todosLosSeguros.subList(startIndex, endIndex);
        
        // Calcular total de páginas
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        // Construir respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("data", segurosPagina);
        response.put("currentPage", page);
        response.put("pageSize", size);
        response.put("totalElements", totalElements);
        response.put("totalPages", totalPages);
        response.put("first", page == 0);
        response.put("last", page >= totalPages - 1);
        
        return response;
    }

    @Override
    public SeguroSalud saveSeguro(SeguroSaludDto dto) {
        // Verificar si ya existe un seguro con el mismo código
        if (dto.getCodigoPlan() != null && seguroRepository.existsByCodigoPlan(dto.getCodigoPlan())) {
            throw new RuntimeException("Ya existe un seguro con el código: " + dto.getCodigoPlan());
        }

        SeguroSalud nuevo = new SeguroSalud();
        // Valores iniciales
        nuevo.setPrestadores(new ArrayList<>());
        nuevo.setVisible(true);

        actualizarDatosEntidad(dto, nuevo);

        return seguroRepository.save(nuevo);
    }

    @Override
    public SeguroSalud updateSeguro(Long id, SeguroSaludDto dto) {
        SeguroSalud existente = seguroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seguro no encontrado con ID: " + id));

        // Verificar si el código ya está siendo usado por otro seguro
        if (dto.getCodigoPlan() != null && !dto.getCodigoPlan().equals(existente.getCodigoPlan())) {
            if (seguroRepository.existsByCodigoPlan(dto.getCodigoPlan())) {
                throw new RuntimeException("Ya existe otro seguro con el código: " + dto.getCodigoPlan());
            }
        }

        actualizarDatosEntidad(dto, existente);

        return seguroRepository.save(existente);
    }

    @Override
    public void deleteSeguro(Long id) {
        if (seguroRepository.existsById(id)) {
            seguroRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar, el seguro no existe.");
        }
    }

    @Override
    public SeguroSalud cambiarVisibilidad(Long id) {
        SeguroSalud seguro = seguroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seguro no encontrado"));
        seguro.setVisible(seguro.getVisible() == null || !seguro.getVisible());
        return seguroRepository.save(seguro);
    }

    @Override
    public Map<String, Object> cargaMasiva(List<SeguroSaludDto> segurosDto) {
        Map<String, Object> response = new HashMap<>();
        int guardados = 0;
        int errores = 0;
        List<String> mensajesError = new ArrayList<>();

        for (SeguroSaludDto dto : segurosDto) {
            try {
                saveSeguro(dto); // Reutilizamos saveSeguro
                guardados++;
            } catch (Exception e) {
                errores++;
                mensajesError.add("Error en seguro " + dto.getCodigoPlan() + ": " + e.getMessage());
            }
        }

        response.put("mensaje", "Se cargaron " + guardados + " seguros correctamente.");
        if (errores > 0) {
            response.put("errores", errores);
            response.put("detallesErrores", mensajesError);
        }
        
        return response;
    }

    private void actualizarDatosEntidad(SeguroSaludDto dto, SeguroSalud entidad) {
        // 1. Campos básicos
        entidad.setCodigoPlan(dto.getCodigoPlan());
        entidad.setNombrePlan(dto.getNombrePlan());
        entidad.setPlan(dto.getPlan());
        entidad.setPrecioBase(dto.getPrecioBase());
        entidad.setPuntaje(dto.getPuntaje());
        entidad.setTopeAnualUf(dto.getTopeAnualUf());
        entidad.setHospitalaria(dto.getHospitalaria());
        entidad.setAmbulatoria(dto.getAmbulatoria());
        entidad.setUrgencia(dto.getUrgencia());
        entidad.setImagenContrato(dto.getImagenContrato());

        // 2. Campos opcionales
        if (dto.getPreferente() != null) {
            entidad.setPreferente(dto.getPreferente());
        }

        if (dto.getVisible() != null) {
            entidad.setVisible(dto.getVisible());
        }

        // 3. Logo
        if (dto.getLogo() != null && !dto.getLogo().isEmpty()) {
            entidad.setLogo(dto.getLogo());
        } else {
            entidad.setLogo(obtenerRutaLogo(dto.getNombrePlan()));
        }

        // 4. Prestadores
        if (dto.getPrestadores() != null) {
            entidad.setPrestadores(dto.getPrestadores());
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
