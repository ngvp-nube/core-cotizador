package com.example._cotizador.repository;

import com.example._cotizador.entity.SeguroVida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguroVidaRepository extends JpaRepository<SeguroVida, Long> {

    List<SeguroVida> findBySegNombre(String segNombre);
    List<SeguroVida> findBySegCodigo(String segCodigo);
    List<SeguroVida> findAllByOrderByIdAsc();
    List<SeguroVida> findBySegPrecioLessThan(Double precioMaximo);
}
