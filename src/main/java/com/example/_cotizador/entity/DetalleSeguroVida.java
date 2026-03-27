package com.example._cotizador.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_seguros_vida", indexes = {
    @Index(name = "idx_detalle_seguro_vida_codigo", columnList = "seg_codigo")
})
public class DetalleSeguroVida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50, name = "seg_codigo")
    private String segCodigo;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "pdf_data", nullable = false, columnDefinition="bytea")
    private byte[] pdfData;

    public DetalleSeguroVida() {}
    
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
