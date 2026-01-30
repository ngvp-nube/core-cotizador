package com.example._cotizador.dto;

import lombok.*;


@NoArgsConstructor


public class DetallePlanDto {

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private String codigo;
    private String file;

    public DetallePlanDto(String codigo, String file) {
        this.codigo = codigo;
        this.file = file;
    }

}
