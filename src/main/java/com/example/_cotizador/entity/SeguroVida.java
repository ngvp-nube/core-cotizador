package com.example._cotizador.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "seguro_vida")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeguroVida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "boolean default true")
    private Boolean visible = true;

    @Column(unique = true)
    private String segCodigo;
    private String segNombre;
    private Double segPrecio;
}
