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

import com.argus.library.models.Autor;
import com.argus.library.models.Pais;

import jakarta.servlet.http.HttpSession;

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

	@GetMapping("/nuevo")
	public String crearAutor(Model model, HttpSession session) {
        String uriPaises = "http://localhost:5032/api/Pais";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Pais[]> responsePaises = restTemplate.getForEntity(uriPaises, Pais[].class);
        
        List<Pais> paises = Arrays.asList(responsePaises.getBody());
		
        model.addAttribute("paises", paises);
		model.addAttribute("autor", new Autor());
        model.addAttribute("crear", true);
        session.setAttribute("crear", true);
        
        return "formautor";
	}
	
	@GetMapping("/editar/{id}")
	public String editarAutor(@PathVariable int id, Model model, HttpSession session) {
        String uri = "http://localhost:5032/api/Autor/" + id;
        String uriPaises = "http://localhost:5032/api/Pais";
        RestTemplate restTemplate = new RestTemplate();      
        
        try {
            ResponseEntity<Autor> response = restTemplate.getForEntity(uri, Autor.class);
            Autor autor = response.getBody();
    		
            ResponseEntity<Pais[]> responsePaises = restTemplate.getForEntity(uriPaises, Pais[].class);
            List<Pais> paises = Arrays.asList(responsePaises.getBody());
            
            model.addAttribute("paises", paises);
            model.addAttribute("autor", autor);
            model.addAttribute("crear", false);
            
            session.setAttribute("AutorID", autor.getAutorID());
            session.setAttribute("crear", false);
            
            return "formautor";
        }
        catch (HttpClientErrorException e) {
            System.out.println("Codigo de error: " + e.getStatusCode());
            return "redirect:/autores";
        }
	}
	
	
	@PostMapping("/guardar")
	public String saveAutor(@ModelAttribute Autor autor, Model model, HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:5032/api/Autor";
        
        if(session.getAttribute("crear") == null) {
        	return "redirect:/autores";
        };
        
        Boolean crear = (Boolean) session.getAttribute("crear");
        
        if (crear) {
            restTemplate.postForEntity(uri, autor, Autor.class);
            session.removeAttribute("crear");
            
            return "redirect:/autores";
        }
        else {
        	int AutorID = (int) session.getAttribute("AutorID");

        	uri = uri + "/" + AutorID;
        	restTemplate.put(uri, autor);
            session.removeAttribute("crear");
            
            return "redirect:/autores";            
        }
        
	}
}
