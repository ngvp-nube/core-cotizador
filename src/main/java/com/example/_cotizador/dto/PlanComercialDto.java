package com.example._cotizador.dto;

import lombok.Data;
import java.util.List;

@Data
public class PlanComercialDto {
    // No ponemos el ID porque al cargar masivamente no suele venir
    private String codigoPlan;
    private String nombrePlan; // Nombre de la Isapre
    private String plan;       // Nombre comercial
    private Double precioBase;
    private Double puntaje;
    private Integer hospitalaria;
    private Integer urgencia;
    private Integer ambulatoria;
    private Integer topeAnualUf;
    private List<String> prestadores;
}