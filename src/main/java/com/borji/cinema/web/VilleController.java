package com.borji.cinema.web;


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

import com.borji.cinema.dao.VilleRepository;
import com.borji.cinema.entities.Ville;

@Controller
@RequestMapping("/ville/")
public class VilleController {
	@Autowired 
	private VilleRepository vp;
	
	@GetMapping("add_ville")
    public String showSignUpForm(Ville ville) {
        return "add_ville";
    }

	@GetMapping(path="/villeList")
	public String ville(Model model,@RequestParam(name="page", defaultValue ="0" )int page,
			@RequestParam(name="size" , defaultValue ="5" )int size,
			@RequestParam (name="keyword", defaultValue ="")String nom	){
		Page<Ville> v=vp.findByNameContains(nom,PageRequest.of(page,size));
		model.addAttribute("listPatients",v.getContent());
		model.addAttribute("pages",new int[v.getTotalPages()]);
		model.addAttribute("currentpage",page);
		model.addAttribute("keyword",nom);
	
	return "villeList";
	}
	  @GetMapping("edit/{id}")
	    public String showUpdateForm(@PathVariable("id") long id, Model model) {
	        Ville ville = vp.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid Ville Id:" + id));
	        model.addAttribute("ville", ville);
	        return "update_ville";
	    }
	  
	  @PostMapping("update/{id}")
	    public String updateStudent(@PathVariable("id") long id, @Valid Ville ville, BindingResult result,
	        Model model) {
	        if (result.hasErrors()) {
	            ville.setId(id);
	            return "update_ville";
	        }
	        vp.save(ville);
	        
	        return "villeList";
	  }
	  
	@PostMapping("add")
    public String addVille(@Valid Ville ville, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_ville";
        }

        vp.save(ville);
        return "redirect:villeList";
    }
	 @GetMapping("delete/{id}")
	    public String deleteStudent(@PathVariable("id") long id, Model model) {
	        Ville student = vp.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
	        vp.delete(student);
	       
	        return "villeList";
	    }


	
	}



