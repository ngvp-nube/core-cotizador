package com.example._cotizador.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "region")
public class Region {

    @Id
    // NOTA: No usamos @GeneratedValue porque queremos asignar manualmente
    // los IDs oficiales (1, 2... 13, 15, 16) para que coincidan con tu Front.
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;
}
