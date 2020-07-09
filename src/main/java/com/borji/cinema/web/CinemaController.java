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
import com.borji.cinema.dao.VilleRepository;
import com.borji.cinema.entities.Cinema;
import com.borji.cinema.entities.Ville;

@Controller
@RequestMapping("/cinema/")
public class CinemaController {
	@Autowired 
	private VilleRepository vp;
	@Autowired 
	private CinemaRepository cp;
	
	@GetMapping("add_cinema")
    public String showSignUpForm(Cinema cinema, Model model,@RequestParam(name="page", defaultValue ="0" )int page,
			@RequestParam(name="size" , defaultValue ="5" )int size,
			@RequestParam (name="keyword", defaultValue ="")String nom	 ) {
		Page<Ville> v=vp.findByNameContains(nom,PageRequest.of(page,size));
		model.addAttribute("listville", v);
        return "add_cinema";
    }

	@GetMapping(path="/cinemaList")
	public String ville(Model model,@RequestParam(name="page", defaultValue ="0" )int page,
			@RequestParam(name="size" , defaultValue ="5" )int size,
			@RequestParam (name="keyword", defaultValue ="")String nom	){
		Page<Cinema> v=cp.findByNameContains(nom,PageRequest.of(page,size));
		model.addAttribute("listPatients",v.getContent());
		model.addAttribute("pages",new int[v.getTotalPages()]);
		model.addAttribute("currentpage",page);
		model.addAttribute("keyword",nom);
	
	return "cinemaList";
	}
	  @GetMapping("edit/{id}")
	    public String showUpdateForm(@PathVariable("id") long id, Model model
	 ) {
	        Cinema cinema = cp.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid cinema Id:" + id));  
	    	List<Ville> v=(List<Ville>) vp.findAll();
			model.addAttribute("listville", v);
			model.addAttribute("cinema", cinema);

	        return "update_cinema";
	    }
	  
	  @PostMapping("update/{id}")
	    public String updateStudent(@PathVariable("id") long id, @Valid Cinema cinema, BindingResult result,
	        Model model) {
	        if (result.hasErrors()) {
	        	cinema.setId(id);
	            return "update_cinema";
	        }
	        cp.save(cinema);
	        
	        return "cinemaList";
	  }
	  
	@PostMapping("add")
    public String addVille(@Valid Cinema cinema, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_cinema";
        }

        cp.save(cinema);
        return "redirect:cinemaList";
    }
	 @GetMapping("delete/{id}")
	    public String deleteStudent(@PathVariable("id") long id, Model model) {
	        Cinema cinema = cp.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid cinema Id:" + id));
	        cp.delete(cinema);
	       
	        return "cinemaList";
	    }
	 


	
	}



