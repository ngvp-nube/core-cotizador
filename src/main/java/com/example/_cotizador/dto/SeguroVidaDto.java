package com.example._cotizador.dto;

import lombok.Data;

@Data
public class SeguroVidaDto {
    private String segCodigo;
    private String segNombre;
    private Double segPrecio;
    private Boolean visible;
}
