package com.example._cotizador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.example._cotizador")
public class CotizadorApplication {

	static void main(String[] args) {
		SpringApplication.run(CotizadorApplication.class, args);
	}

}
