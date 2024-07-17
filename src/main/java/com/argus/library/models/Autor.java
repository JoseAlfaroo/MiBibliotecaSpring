package com.argus.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Autor {
	private int autorID;
	private String nombresAutor;
	private String apellidosAutor;
	private int paisID;
}
