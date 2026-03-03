package com.example._cotizador.loader;

import com.example._cotizador.entity.Region;
import com.example._cotizador.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RegionLoader implements CommandLineRunner {

    @Autowired
    private RegionRepository regionRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo insertamos si la tabla está vacía para no duplicar
        if (regionRepository.count() == 0) {
            List<Region> regiones = Arrays.asList(
                    new Region(1L, "Arica y Parinacota"),
                    new Region(2L, "Tarapacá"),
                    new Region(3L, "Antofagasta"),
                    new Region(4L, "Atacama"),
                    new Region(5L, "Coquimbo"),
                    new Region(6L, "Valparaíso"),
                    new Region(7L, "Metropolitana de Santiago"),
                    new Region(8L, "O’Higgins"),
                    new Region(9L, "Maule"),
                    new Region(10L, "Ñuble"),
                    new Region(11L, "Biobío"),
                    new Region(12L, "La Araucanía"),
                    new Region(13L, "Los Ríos"),
                    new Region(14L, "Los Lagos"),
                    new Region(15L, "Aysén"),
                    new Region(16L, "Magallanes y Antártica Chilena")
            );

            regionRepository.saveAll(regiones);
            System.out.println("✅ --- CARGA DE DATOS: Regiones cargadas exitosamente en la BD ---");
        }
    }
}
