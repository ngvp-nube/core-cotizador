package com.example._cotizador.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // <--- IMPORTANTE
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "plan_comercial")
@JsonIgnoreProperties(ignoreUnknown = true) // <--- ESTO ES VITAL: Ignora campos del JSON que no estén aquí (como imagenContrato)
public class PlanComercial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "boolean default true")
    private Boolean visible = true;

    @Column(unique = true)
    private String codigoPlan;
    private String nombrePlan;
    private String plan;
    private Boolean preferente;
    private String logo;
    private Double precioBase;
    private Double puntaje;
    private Integer hospitalaria;
    private Integer urgencia;
    private Integer ambulatoria;
    private Integer topeAnualUf;

    @ElementCollection
    @CollectionTable(name = "plan_prestadores", joinColumns = @JoinColumn(name = "plan_id"))
    @Column(name = "prestador")
    private List<String> prestadores;
}
