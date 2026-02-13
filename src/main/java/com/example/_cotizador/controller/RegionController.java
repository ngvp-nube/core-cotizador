package com.example._cotizador.controller;

import com.example._cotizador.dto.RegionDto;
import com.example._cotizador.entity.Region;
import com.example._cotizador.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/regiones")
@CrossOrigin(origins = "http://localhost:4200") // ¡Clave para que Angular funcione!
public class RegionController {

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping
    public List<RegionDto> obtenerTodas() {
        // Buscamos todas las regiones ordenadas por ID
        List<Region> entidades = regionRepository.findAll(Sort.by("id"));

        // Convertimos Entity -> DTO
        return entidades.stream()
                .map(region -> new RegionDto(region.getId(), region.getNombre()))
                .collect(Collectors.toList());
    }
}
