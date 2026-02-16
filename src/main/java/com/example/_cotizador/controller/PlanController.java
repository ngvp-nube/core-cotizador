package com.example._cotizador.controller;

import com.example._cotizador.dto.PlanComercialDto;
import com.example._cotizador.entity.PlanComercial;
import com.example._cotizador.services.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/planes")
@CrossOrigin(origins = "http://localhost:4200")
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