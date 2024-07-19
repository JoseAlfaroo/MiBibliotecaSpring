package com.argus.library.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.argus.library.models.Genero;
import com.argus.library.models.Pais;

import jakarta.servlet.http.HttpSession;

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
    
    
    @GetMapping("/nuevo")
    public String crearGenero(Model model, HttpSession session) {
        model.addAttribute("genero", new Genero());
        model.addAttribute("crear", true);
        session.setAttribute("crear", true);
        
        return "formgenero";
    }
    
    
    @GetMapping("/editar/{id}")
    public String editarGenero(@PathVariable int id, Model model, HttpSession session) {
        String uri = "http://localhost:5032/api/Genero/" + id;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Genero> response = restTemplate.getForEntity(uri, Genero.class);
            Genero genero = response.getBody();
            model.addAttribute("genero", genero);
            
            model.addAttribute("crear", false);
            session.setAttribute("GeneroID", genero.getGeneroID());
            session.setAttribute("crear", false);
            
            return "formgenero";
        } 
        catch (HttpClientErrorException e) {
            System.out.println("Codigo de error: " + e.getStatusCode());
            return "redirect:/generos";
        }
    }
    
    
    
    @PostMapping("/guardar")
    private String saveGenero(@ModelAttribute Genero genero, Model model, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:5032/api/Genero";
        
        if(session.getAttribute("crear") == null) {
            return "redirect:/generos";
        };
        
        Boolean crear = (Boolean) session.getAttribute("crear");

        if (crear) {
        	
            restTemplate.postForEntity(uri, genero, Genero.class);
            session.removeAttribute("crear");
            
            return "redirect:/generos";
        } 
        else {
        	int GeneroID = (int) session.getAttribute("GeneroID");

            uri = uri + "/" + GeneroID;
            restTemplate.put(uri, genero);
            session.removeAttribute("crear");
        	
            return "redirect:/generos";
        }

    }
    
    
    @PostMapping("/eliminar/{id}")
    private String deleteGenero(@PathVariable int id) {
        String uri = "http://localhost:5032/api/Genero/" + id;
        RestTemplate restTemplate = new RestTemplate();
        
        try {
        	restTemplate.delete(uri);
        }
        catch (HttpClientErrorException e) {
            System.out.println("Codigo de error: " + e.getStatusCode());
            return "redirect:/generos";
        }
    	
        return "redirect:/generos";
    }
    
    
}
