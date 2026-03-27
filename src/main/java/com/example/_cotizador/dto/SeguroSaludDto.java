package com.example._cotizador.dto;

import lombok.Data;
import java.util.List;

@Data
public class SeguroSaludDto {
    private Long id;
    private String codigoPlan;
    private Boolean preferente;
    private String nombrePlan;
    private String logo;
    private String plan;
    private List<String> prestadores;
    private Integer hospitalaria;
    private Integer urgencia;
    private Integer ambulatoria;
    private Integer topeAnualUf;
    private Double puntaje;
    private Double precioBase;
    private String imagenContrato;
    private Boolean visible;
}
