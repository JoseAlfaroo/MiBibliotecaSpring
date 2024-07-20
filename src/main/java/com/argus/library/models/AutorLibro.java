package com.argus.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorLibro {
    private int libroID;
    private int autorID;
    private Autor autor;
}
