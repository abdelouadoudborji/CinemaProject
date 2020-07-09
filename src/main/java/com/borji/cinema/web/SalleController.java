package com.borji.cinema.web;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.borji.cinema.dao.CinemaRepository;
import com.borji.cinema.dao.SalleRepository;
import com.borji.cinema.dao.VilleRepository;
import com.borji.cinema.entities.Cinema;
import com.borji.cinema.entities.Salle;
import com.borji.cinema.entities.Ville;

@Controller
@RequestMapping("/salle/")
public class SalleController {
	@Autowired 
	private SalleRepository vp;
	@Autowired 
	private CinemaRepository cp;
	
	@GetMapping("add_salle")
    public String showSignUpForm(Salle salle, Model model,@RequestParam(name="page", defaultValue ="0" )int page,
			@RequestParam(name="size" , defaultValue ="5" )int size,
			@RequestParam (name="keyword", defaultValue ="")String nom	 ) {
		Page<Cinema> v=cp.findByNameContains(nom,PageRequest.of(page,size));
		model.addAttribute("listville", v);
        return "add_salle";
    }

	@GetMapping(path="/salleList")
	public String ville(Model model,@RequestParam(name="page", defaultValue ="0" )int page,
			@RequestParam(name="size" , defaultValue ="5" )int size,
			@RequestParam (name="keyword", defaultValue ="")String nom	){
		Page<Salle> v=vp.findByNameContains(nom,PageRequest.of(page,size));
		model.addAttribute("listPatients",v.getContent());
		model.addAttribute("pages",new int[v.getTotalPages()]);
		model.addAttribute("currentpage",page);
		model.addAttribute("keyword",nom);
	
	return "salleList";
	}
	  @GetMapping("edit/{id}")
	    public String showUpdateForm(@PathVariable("id") long id, Model model
	 ) {
	        Salle salle = vp.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Salle Id:" + id));  
	    	List<Cinema> v=(List<Cinema>) cp.findAll();
			model.addAttribute("listville", v);
			model.addAttribute("salle", salle);

	        return "update_salle";
	    }
	  
	  @PostMapping("update/{id}")
	    public String updateStudent(@PathVariable("id") long id, @Valid Salle cinema, BindingResult result,
	        Model model) {
	        if (result.hasErrors()) {
	        	cinema.setId(id);
	            return "update_salle";
	        }
	        vp.save(cinema);
	        
	        return "salleList";
	  }
	  
	@PostMapping("add")
    public String addVille(@Valid Salle cinema, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_salle";
        }

        vp.save(cinema);
        return "redirect:salleList";
    }
	 @GetMapping("delete/{id}")
	    public String deleteStudent(@PathVariable("id") long id, Model model) {
	        Salle cinema = vp.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid salle Id:" + id));
	        vp.delete(cinema);
	       
	        return "salleList";
	    }
	 


	
	}



