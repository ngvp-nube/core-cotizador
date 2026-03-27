package com.example._cotizador.dto;

public class DetalleSeguroVidaDto {
    
    // Campos básicos
    private Long id;
    private String segCodigo;
    private String fileName;
    private String contentType;
    private byte[] pdfData;
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSegCodigo() { return segCodigo; }
    public void setSegCodigo(String segCodigo) { this.segCodigo = segCodigo; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    
    public byte[] getPdfData() { return pdfData; }
    public void setPdfData(byte[] pdfData) { this.pdfData = pdfData; }
}
