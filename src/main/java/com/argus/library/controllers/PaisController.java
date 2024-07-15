package com.argus.library.controllers;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.argus.library.models.Pais;

@Controller
@RequestMapping("/paises") 
public class PaisController {
	
	@GetMapping(value={"", "/"})
	private String getAllPaises(Model model) {
		String uri = "http://localhost:5032/api/Pais";
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Pais[]> response = restTemplate.getForEntity(uri, Pais[].class);
		
		List<Pais> paises = Arrays.asList(response.getBody());
		model.addAttribute("paises", paises);
		
		return "paises";
	}
	
	
	@GetMapping("/nuevo")
	public String crearPais(Model model) {
	    model.addAttribute("pais", new Pais());
	    return "formpais";
	}
	
	
	@PostMapping("/guardar")
	private String createPais(@ModelAttribute Pais pais) {
		String uri = "http://localhost:5032/api/Pais";
		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.postForEntity(uri, pais, Pais.class);
		
		return "redirect:/paises";
	}
	
	
}
