package com.example._cotizador.controller;

import com.example._cotizador.dto.DetallePlanDto;
import com.example._cotizador.entity.DetallePlan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example._cotizador.services.DetailService;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class DetallePlanController {

    private final DetailService service;

    public DetallePlanController(DetailService service) {
        this.service = service;
    }


    // Crear un detallePlan
    @PostMapping("/createdetail/masivo")
    public ResponseEntity<List<DetallePlan>> createDetallePlanMasivo(@RequestBody java.util.List<DetallePlanDto> dtos) {
        List<DetallePlan> saved = service.saveDetallePlansMasivo(dtos);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/detalleplan/{codigo}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String codigo) {
        DetallePlan plan = service.getByCodigo(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe contrato"));

        return ResponseEntity.ok()
                .header("Content-Type", plan.getContentType() != null ? plan.getContentType() : "application/pdf")
                .header("Content-Disposition", "attachment; filename=\"" +
                        (plan.getFileName() != null ? plan.getFileName() : (codigo + ".pdf")) + "\"")
                .body(plan.getPdfData());
    }

    @GetMapping("/detalleplan/{codigo}/pdf/view")
    public ResponseEntity<byte[]> viewPdf(@PathVariable String codigo) {
        DetallePlan plan = service.getByCodigo(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe contrato"));

        return ResponseEntity.ok()
                .header("Content-Type", plan.getContentType() != null ? plan.getContentType() : "application/pdf")
                .header("Content-Disposition", "inline; filename=\"" +
                        (plan.getFileName() != null ? plan.getFileName() : (codigo + ".pdf")) + "\"")
                .body(plan.getPdfData());
    }



}
