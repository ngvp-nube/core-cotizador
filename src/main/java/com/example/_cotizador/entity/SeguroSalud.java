package com.example._cotizador.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "seguro_salud")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeguroSalud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "boolean default true")
    private Boolean visible = true;

    @Column(unique = true)
    private String codigoPlan;
    private Boolean preferente;
    private String nombrePlan;
    private String logo;
    private String plan;
    private Integer hospitalaria;
    private Integer urgencia;
    private Integer ambulatoria;
    private Integer topeAnualUf;
    private Double puntaje;
    private Double precioBase;
    private String imagenContrato;

    @ElementCollection
    @CollectionTable(name = "seguro_salud_prestadores", joinColumns = @JoinColumn(name = "seguro_id"))
    @Column(name = "prestador")
    private List<String> prestadores;
}
