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

import com.argus.library.models.Pais;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/paises") 
public class PaisController {

    @GetMapping
    private String getAllPaises(Model model) {
        String uri = "http://localhost:5032/api/Pais";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Pais[]> response = restTemplate.getForEntity(uri, Pais[].class);
        List<Pais> paises = Arrays.asList(response.getBody());
        model.addAttribute("paises", paises);

    	return "paises";
    }

    
    @GetMapping("/nuevo")
    public String crearPais(Model model, HttpSession session) {
        model.addAttribute("pais", new Pais());
        model.addAttribute("crear", true);
        session.setAttribute("crear", true);
        
        return "formpais";
    }

    
    @GetMapping("/editar/{id}")
    public String editarPais(@PathVariable int id, Model model, HttpSession session) {
        String uri = "http://localhost:5032/api/Pais/" + id;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Pais> response = restTemplate.getForEntity(uri, Pais.class);
            Pais pais = response.getBody();
            model.addAttribute("pais", pais);
            
            model.addAttribute("crear", false);
            session.setAttribute("PaisID", pais.getPaisID());
            session.setAttribute("crear", false);
            
            return "formpais";
        } 
        catch (HttpClientErrorException e) {
            System.out.println("Codigo de error: " + e.getStatusCode());
            return "redirect:/paises";
        }
    }

    
    @PostMapping("/guardar")
    private String savePais(@ModelAttribute Pais pais, Model model, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:5032/api/Pais";
        
        if(session.getAttribute("crear") == null) {
        	return "redirect:/paises";
        };
        
        Boolean crear = (Boolean) session.getAttribute("crear");

        if (crear) {
        	
            restTemplate.postForEntity(uri, pais, Pais.class);
            session.removeAttribute("crear");
            
            return "redirect:/paises";
        } 
        else {
        	int PaisID = (int) session.getAttribute("PaisID");

            uri = uri + "/" + PaisID;
            restTemplate.put(uri, pais);
            session.removeAttribute("crear");
        	
            return "redirect:/paises";
        }

    }
    
    
    @PostMapping("/eliminar/{id}")
    private String deletePais(@PathVariable int id) {
        String uri = "http://localhost:5032/api/Pais/" + id;
        RestTemplate restTemplate = new RestTemplate();
        
        try {
        	restTemplate.delete(uri);
        }
        catch (HttpClientErrorException e) {
            System.out.println("Codigo de error: " + e.getStatusCode());
            return "redirect:/paises";
        }
    	
    	return "redirect:/paises";
    }
}