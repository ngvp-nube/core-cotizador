package com.example._cotizador.repository; // O tu paquete correspondiente

// Esta interfaz actúa como un "filtro" para no traer el PDF pesado
public interface DetallePlanResumen {
    Long getId();
    String getCodigo();
    String getFileName();
    String getContentType();
    // ¡NOTA: No ponemos getPdfData() aquí para que sea rápido!
}
