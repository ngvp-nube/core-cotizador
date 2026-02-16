package com.example._cotizador.dto;

import jakarta.validation.constraints.NotBlank;

public class DetallePlanDto {

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El PDF en Base64 es obligatorio")
    private String pdfBase64;

    private String fileName;

    private String contentType;

    public DetallePlanDto() {}

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPdfBase64() {
        return pdfBase64;
    }

    public void setPdfBase64(String pdfBase64) {
        this.pdfBase64 = pdfBase64;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
