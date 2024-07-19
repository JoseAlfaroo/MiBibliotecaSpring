package com.argus.library.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.argus.library.models.Genero;

@Controller
@RequestMapping("/generos") 
public class GeneroController {
	
    @GetMapping
    private String getAllGeneros(Model model) {
        String uri = "http://localhost:5032/api/Genero";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Genero[]> response = restTemplate.getForEntity(uri, Genero[].class);
        List<Genero> generos = Arrays.asList(response.getBody());
        model.addAttribute("generos", generos);

    	return "generos";
    }
    
    
    
}
