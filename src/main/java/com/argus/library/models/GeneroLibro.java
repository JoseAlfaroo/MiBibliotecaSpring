package com.argus.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneroLibro {
    private int libroID;
    private int generoID;
    private Genero genero;
}
