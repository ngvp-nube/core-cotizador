package com.example._cotizador.controller;

import com.example._cotizador.dto.SeguroSaludDto;
import com.example._cotizador.entity.SeguroSalud;
import com.example._cotizador.services.SeguroSaludService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/seguros-salud")
@CrossOrigin(origins = "http://localhost:4200", 
    exposedHeaders = {"X-Current-Page", "X-Page-Size", "X-Total-Elements", "X-Total-Pages", "X-First-Page", "X-Last-Page"})
public class SeguroSaludController {

    private final SeguroSaludService seguroSaludService;

    public SeguroSaludController(SeguroSaludService seguroSaludService) {
        this.seguroSaludService = seguroSaludService;
    }

    /**enpoint para carga de seguros de salud masivos**/
    @PostMapping("/carga-masiva")
    public ResponseEntity<?> cargarMasivamente(@RequestBody List<SeguroSaludDto> segurosDto) {
        try {
            Map<String, Object> response = seguroSaludService.cargaMasiva(segurosDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "❌ Error crítico: " + e.getMessage()));
        }
    }

    /**
     Enpoint para obtener los seguros de salud
     **/
    @GetMapping
    public ResponseEntity<List<SeguroSalud>> obtenerTodos() {
        return ResponseEntity.ok(seguroSaludService.getAllSeguros());
    }

    /**
     Endpoint para obtener los seguros de salud con paginación usando headers
     **/
    @GetMapping("/paginados")
    public ResponseEntity<List<SeguroSalud>> obtenerTodosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> response = seguroSaludService.getSegurosPaginados(page, size);
            
            // Extraer datos y metadata
            List<SeguroSalud> seguros = (List<SeguroSalud>) response.get("data");
            int currentPage = (int) response.get("currentPage");
            int pageSize = (int) response.get("pageSize");
            int totalElements = (int) response.get("totalElements");
            int totalPages = (int) response.get("totalPages");
            boolean first = (boolean) response.get("first");
            boolean last = (boolean) response.get("last");
            
            // Construir headers de paginación
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Current-Page", String.valueOf(currentPage));
            headers.add("X-Page-Size", String.valueOf(pageSize));
            headers.add("X-Total-Elements", String.valueOf(totalElements));
            headers.add("X-Total-Pages", String.valueOf(totalPages));
            headers.add("X-First-Page", String.valueOf(first));
            headers.add("X-Last-Page", String.valueOf(last));
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(seguros);
                    
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearSeguro(@RequestBody SeguroSaludDto seguroDto) {
        try {
            return ResponseEntity.ok(seguroSaludService.saveSeguro(seguroDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "❌ Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSeguro(@PathVariable Long id) {
        try {
            seguroSaludService.deleteSeguro(id);
            return ResponseEntity.ok(Map.of("mensaje", "Seguro de salud eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/visibilidad")
    public ResponseEntity<?> cambiarVisibilidad(@PathVariable Long id) {
        try {
            SeguroSalud actualizado = seguroSaludService.cambiarVisibilidad(id);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSeguro(@PathVariable Long id, @RequestBody SeguroSaludDto dto) {
        try {
            return ResponseEntity.ok(seguroSaludService.updateSeguro(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Error al actualizar: " + e.getMessage()));
        }
    }
}
