package com.example._cotizador.controller;

import com.example._cotizador.dto.DetalleSeguroVidaDto;
import com.example._cotizador.entity.DetalleSeguroVida;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example._cotizador.services.DetalleSeguroVidaService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class DetalleSeguroVidaController {

    private final DetalleSeguroVidaService service;

    public DetalleSeguroVidaController(DetalleSeguroVidaService service) {
        this.service = service;
    }

    /**
     * Endpoint para ingresar los contratos PDF de seguros de vida masivamente
     */
    @PostMapping("/detallesegurovida/masivo")
    public ResponseEntity<List<DetalleSeguroVida>> createDetalleSeguroVidaMasivo(@RequestBody List<DetalleSeguroVidaDto> dtos) {
        try {
            System.out.println("Recibidos " + dtos.size() + " DTOs");
            for (DetalleSeguroVidaDto dto : dtos) {
                System.out.println("DTO: segCodigo=" + dto.getSegCodigo() + ", fileName=" + dto.getFileName());
            }
            
            List<DetalleSeguroVida> saved = service.saveDetalleSegurosVidaMasivo(dtos);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error en createDetalleSeguroVidaMasivo: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint para obtener los contratos PDF de seguros de vida
     */
    @GetMapping("/detallesegurovida/{segCodigo}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String segCodigo) {
        DetalleSeguroVida detalle = service.getByCodigo(segCodigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe contrato"));

        return ResponseEntity.ok()
                .header("Content-Type", detalle.getContentType() != null ? detalle.getContentType() : "application/pdf")
                .header("Content-Disposition", "attachment; filename=\"" +
                        (detalle.getFileName() != null ? detalle.getFileName() : (segCodigo + ".pdf")) + "\"")
                .body(detalle.getPdfData());
    }

    /**
     * Endpoint para obtener los contratos y mostrarlos en el front
     */
    @GetMapping("/detallesegurovida/{segCodigo}/pdf/view")
    public ResponseEntity<byte[]> viewPdf(@PathVariable String segCodigo) {
        DetalleSeguroVida detalle = service.getByCodigo(segCodigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe contrato"));

        return ResponseEntity.ok()
                .header("Content-Type", detalle.getContentType() != null ? detalle.getContentType() : "application/pdf")
                .header("Content-Disposition", "inline; filename=\"" +
                        (detalle.getFileName() != null ? detalle.getFileName() : (segCodigo + ".pdf")) + "\"")
                .body(detalle.getPdfData());
    }
}
