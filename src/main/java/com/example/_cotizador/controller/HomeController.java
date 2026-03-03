package com.example._cotizador.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "¡Bienvenido a Core Cotizador! La aplicación está funcionando correctamente.";
    }

    @GetMapping("/health")
    public String health() {
        return "OK - Aplicación saludable";
    }
}
