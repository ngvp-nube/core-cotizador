package com.example._cotizador.controller;

import com.example._cotizador.dto.DetallePlanDto;
import com.example._cotizador.entity.DetallePlan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example._cotizador.services.DetailService;

@RestController
@RequestMapping("/api")
public class DetallePlanController {

    private final DetailService service;

    public DetallePlanController(DetailService service) {
        this.service = service;
    }


    // Crear un detallePlan
    @PostMapping("/createdetail")
    public ResponseEntity<DetallePlan> createDetallePlan(@RequestBody DetallePlanDto dto) {
        DetallePlan saved = service.saveDetallePlan(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


}
