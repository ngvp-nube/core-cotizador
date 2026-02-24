package com.example._cotizador.services;

import com.example._cotizador.dto.SeguroVidaDto;
import com.example._cotizador.entity.SeguroVida;
import com.example._cotizador.repository.SeguroVidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SeguroVidaServiceImpl implements SeguroVidaService {

    private final SeguroVidaRepository seguroRepository;

    public SeguroVidaServiceImpl(SeguroVidaRepository seguroRepository) {
        this.seguroRepository = seguroRepository;
    }

    @Override
    public List<SeguroVida> getAllSeguros() {
        return seguroRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Map<String, Object> getSegurosPaginados(int page, int size) {
        // Obtener todos los seguros ordenados
        List<SeguroVida> todosLosSeguros = seguroRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        
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
        List<SeguroVida> segurosPagina = todosLosSeguros.subList(startIndex, endIndex);
        
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
    public SeguroVida saveSeguro(SeguroVidaDto dto) {
        SeguroVida nuevo = new SeguroVida();
        // Valores iniciales
        nuevo.setVisible(true);

        actualizarDatosEntidad(dto, nuevo);

        return seguroRepository.save(nuevo);
    }

    @Override
    public SeguroVida updateSeguro(Long id, SeguroVidaDto dto) {
        SeguroVida existente = seguroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seguro de vida no encontrado con ID: " + id));

        actualizarDatosEntidad(dto, existente);

        return seguroRepository.save(existente);
    }

    @Override
    public void deleteSeguro(Long id) {
        if (seguroRepository.existsById(id)) {
            seguroRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se puede eliminar, el seguro de vida no existe.");
        }
    }

    @Override
    public SeguroVida cambiarVisibilidad(Long id) {
        SeguroVida seguro = seguroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seguro de vida no encontrado"));
        seguro.setVisible(seguro.getVisible() == null || !seguro.getVisible());
        return seguroRepository.save(seguro);
    }

    @Override
    public Map<String, Object> cargaMasiva(List<SeguroVidaDto> segurosDto) {
        Map<String, Object> response = new HashMap<>();
        int guardados = 0;
        for (SeguroVidaDto dto : segurosDto) {
            saveSeguro(dto); // Reutilizamos saveSeguro
            guardados++;
        }
        response.put("mensaje", "Se cargaron " + guardados + " seguros de vida.");
        return response;
    }

    private void actualizarDatosEntidad(SeguroVidaDto dto, SeguroVida entidad) {
        entidad.setSegCodigo(dto.getSegCodigo());
        entidad.setSegNombre(dto.getSegNombre());
        entidad.setSegPrecio(dto.getSegPrecio());
        
        if (dto.getVisible() != null) {
            entidad.setVisible(dto.getVisible());
        }
    }
}
