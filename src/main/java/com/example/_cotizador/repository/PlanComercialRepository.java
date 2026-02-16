package com.example._cotizador.repository;

import com.example._cotizador.entity.PlanComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanComercialRepository extends JpaRepository<PlanComercial, Long> {

    List<PlanComercial> findByNombrePlan(String nombreIsapre);

    List<PlanComercial> findByPrecioBaseLessThan(Double precioMaximo);
    List<PlanComercial> findByCodigoPlan(String codigoPlan);
    List<PlanComercial> findAllByOrderByIdAsc();
}
