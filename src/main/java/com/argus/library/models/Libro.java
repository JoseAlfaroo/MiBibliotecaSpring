package com.argus.library.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    private int libroID;
    private String titulo;
    private int añoPublicacion;
    private String portada;
    private String descripcion;
    private List<GeneroLibro> generosLibro;
    private List<AutorLibro> autoresLibro;
}
