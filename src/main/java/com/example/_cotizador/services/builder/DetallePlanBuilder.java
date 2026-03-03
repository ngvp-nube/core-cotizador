package com.example._cotizador.services.builder;

import com.example._cotizador.dto.DetallePlanDto;
import com.example._cotizador.entity.DetallePlan;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class DetallePlanBuilder {

    public DetallePlan buildFromDto(DetallePlanDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser null");
        }
        if (dto.getCodigo() == null || dto.getCodigo().isBlank()) {
            throw new IllegalArgumentException("El codigo es obligatorio");
        }
        if (dto.getPdfBase64() == null || dto.getPdfBase64().isBlank()) {
            throw new IllegalArgumentException("El pdfBase64 es obligatorio");
        }

        byte[] pdfBytes;
        try {
            pdfBytes = Base64.getDecoder().decode(dto.getPdfBase64());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El pdfBase64 no es válido (Base64 inválido)", e);
        }

        DetallePlan entity = new DetallePlan();
        entity.setCodigo(dto.getCodigo().trim());
        entity.setPdfData(pdfBytes);

        String fileName = (dto.getFileName() != null && !dto.getFileName().isBlank())
                ? dto.getFileName().trim()
                : "contrato.pdf";
        entity.setFileName(fileName);

        String contentType = (dto.getContentType() != null && !dto.getContentType().isBlank())
                ? dto.getContentType().trim()
                : "application/pdf";
        entity.setContentType(contentType);

        return entity;
    }
}

