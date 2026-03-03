package com.example._cotizador.dto;

import lombok.Data;
import java.util.List;

@Data
public class PlanComercialDto {
    private String codigoPlan;
    private String nombrePlan;
    private String plan;
    private Double precioBase;
    private Double puntaje;
    private Integer hospitalaria;
    private Integer urgencia;
    private Integer ambulatoria;
    private Integer topeAnualUf;
    private List<String> prestadores;
    private Boolean visible;
    private String logo;
    private Boolean preferente;
}