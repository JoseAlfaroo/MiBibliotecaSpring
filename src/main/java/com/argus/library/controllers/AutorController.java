package com.argus.library.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.argus.library.models.Autor;
import com.argus.library.models.Pais;

@Controller
@RequestMapping("/autores") 
public class AutorController {
	
	@GetMapping
	private String getAllAutores(Model model) {
        String uriAutores = "http://localhost:5032/api/Autor";
        String uriPaises = "http://localhost:5032/api/Pais";
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<Autor[]> responseAutores = restTemplate.getForEntity(uriAutores, Autor[].class);
        ResponseEntity<Pais[]> responsePaises = restTemplate.getForEntity(uriPaises, Pais[].class);
        
        List<Autor> autores = Arrays.asList(responseAutores.getBody());
        List<Pais> paises = Arrays.asList(responsePaises.getBody());
        
        model.addAttribute("autores", autores);
        model.addAttribute("paises", paises);
        
        return "autores";
	}

}
