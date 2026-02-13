package com.example._cotizador.repository;

import com.example._cotizador.entity.PlanComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanComercialRepository extends JpaRepository<PlanComercial, Long> {

    // ¡MAGIA! 🪄
    // Al extender de JpaRepository, ya tienes automáticamente:
    // - saveAll() -> Para tu migración masiva
    // - findAll() -> Para listar
    // - save()    -> Para guardar uno
    // - delete()  -> Para borrar

    // OPCIONAL: Métodos personalizados para el futuro
    // Spring crea la consulta SQL solo por el nombre del método:

    // Buscar todos los planes de "Consalud"
    List<PlanComercial> findByNombrePlan(String nombreIsapre);

    // Buscar planes que cuesten menos de X UF
    List<PlanComercial> findByPrecioBaseLessThan(Double precioMaximo);
    // Spring entenderá automáticamente que debe buscar por la columna 'codigoPlan'
    List<PlanComercial> findByCodigoPlan(String codigoPlan);
}
