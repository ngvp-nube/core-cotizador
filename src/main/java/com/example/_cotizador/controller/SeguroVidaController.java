package com.example._cotizador.controller;

import com.example._cotizador.dto.SeguroVidaDto;
import com.example._cotizador.entity.SeguroVida;
import com.example._cotizador.services.SeguroVidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/seguros-vida")
public class SeguroVidaController {

    private final SeguroVidaService seguroVidaService;

    public SeguroVidaController(SeguroVidaService seguroVidaService) {
        this.seguroVidaService = seguroVidaService;
    }

    @GetMapping
    public List<SeguroVida> getAllSeguros() {
        return seguroVidaService.getAllSeguros();
    }

    @GetMapping("/paginados")
    public Map<String, Object> getSegurosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return seguroVidaService.getSegurosPaginados(page, size);
    }

    @PostMapping
    public SeguroVida createSeguro(@RequestBody SeguroVidaDto seguroDto) {
        return seguroVidaService.saveSeguro(seguroDto);
    }

    @PostMapping("/carga-masiva")
    public Map<String, Object> cargaMasiva(@RequestBody List<SeguroVidaDto> segurosDto) {
        return seguroVidaService.cargaMasiva(segurosDto);
    }

    @PutMapping("/{id}")
    public SeguroVida updateSeguro(@PathVariable Long id, @RequestBody SeguroVidaDto seguroDto) {
        return seguroVidaService.updateSeguro(id, seguroDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeguro(@PathVariable Long id) {
        seguroVidaService.deleteSeguro(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/visibilidad")
    public SeguroVida cambiarVisibilidad(@PathVariable Long id) {
        return seguroVidaService.cambiarVisibilidad(id);
    }
}
