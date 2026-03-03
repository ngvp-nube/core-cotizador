package com.example._cotizador.controller;

import com.example._cotizador.dto.PlanComercialDto;
import com.example._cotizador.entity.PlanComercial;
import com.example._cotizador.services.PlanService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/planes")
@CrossOrigin(origins = "http://localhost:4200", 
    exposedHeaders = {"X-Current-Page", "X-Page-Size", "X-Total-Elements", "X-Total-Pages", "X-First-Page", "X-Last-Page"})
public class PlanController {

    private final PlanService service;
    public PlanController(PlanService service) {
        this.service = service;
    }

    /**enpoint para carga de planes masivos**/
    @PostMapping("/carga-masiva")
    public ResponseEntity<?> cargarMasivamente(@RequestBody List<PlanComercialDto> planesDto) {
        try {
            Map<String, Object> response =  service.cargaMasiva(planesDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "❌ Error crítico: " + e.getMessage()));
        }
    }

    /**
     Enpoint para obtener los planes
     **/
    @GetMapping
    public ResponseEntity<List<PlanComercial>> obtenerTodos() {
        return ResponseEntity.ok(service.getAllPlanes());
    }

    /**
     Endpoint para obtener los planes con paginación usando headers
     **/
    @GetMapping("/paginados")
    public ResponseEntity<List<PlanComercial>> obtenerTodosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> response = service.getPlanesPaginados(page, size);
            
            // Extraer datos y metadata
            List<PlanComercial> planes = (List<PlanComercial>) response.get("data");
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
                    .body(planes);
                    
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPlan(@RequestBody PlanComercialDto planDto) {
        try {
            return ResponseEntity.ok(service.savePlan(planDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "❌ Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPlan(@PathVariable Long id) {
        try {
            service.deletePlan(id);
            return ResponseEntity.ok(Map.of("mensaje", "Plan eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}/visibilidad")
    public ResponseEntity<?> cambiarVisibilidad(@PathVariable Long id) {
        try {
            PlanComercial actualizado = service.cambiarVisibilidad(id);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPlan(@PathVariable Long id, @RequestBody PlanComercialDto dto) {
        try {
            return ResponseEntity.ok(service.updatePlan(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Error al actualizar: " + e.getMessage()));
        }
    }
}