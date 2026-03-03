package com.example._cotizador.repository;

import com.example._cotizador.entity.DetalleSeguroVida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetalleSeguroVidaRepository extends JpaRepository<DetalleSeguroVida, Long> {
    
    Optional<DetalleSeguroVida> findBySegCodigo(String segCodigo);
    
    boolean existsBySegCodigo(String segCodigo);
}
