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

import com.borji.cinema.dao.CategorieRepository;
import com.borji.cinema.dao.VilleRepository;
import com.borji.cinema.entities.Categorie;
import com.borji.cinema.entities.Ville;

@Controller
@RequestMapping("/categorie/")
public class CategorieControlleur {
	@Autowired 
	private CategorieRepository vp;
	
	@GetMapping("add_categorie")
    public String showSignUpForm(Categorie categorie) {
        return "add_categorie";
    }

	@GetMapping(path="/categorieList")
	public String ville(Model model,@RequestParam(name="page", defaultValue ="0" )int page,
			@RequestParam(name="size" , defaultValue ="5" )int size,
			@RequestParam (name="keyword", defaultValue ="")String nom	){
		Page<Categorie> v=vp.findByNameContains(nom,PageRequest.of(page,size));
		model.addAttribute("listPatients",v.getContent());
		model.addAttribute("pages",new int[v.getTotalPages()]);
		model.addAttribute("currentpage",page);
		model.addAttribute("keyword",nom);
	
	return "categorieList";
	}
	  @GetMapping("edit/{id}")
	    public String showUpdateForm(@PathVariable("id") long id, Model model) {
	        Categorie categorie = vp.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid Categorie Id:" + id));
	        model.addAttribute("categorie", categorie);
	        return "update_categorie";
	    }
	  
	  @PostMapping("update/{id}")
	    public String updateCategorie(@PathVariable("id") long id, @Valid Categorie categorie, BindingResult result,
	        Model model) {
	        if (result.hasErrors()) {
	        	categorie.setId(id);
	            return "update_categroie";
	        }
	        vp.save(categorie);
	        
	        return "categorieList";
	  }
	  
	@PostMapping("add")
    public String addCategorie(@Valid Categorie categorie, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_Categorie";
        }

        vp.save(categorie);
        return "categorieList";
    }
	 @GetMapping("delete/{id}")
	    public String deleteStudent(@PathVariable("id") long id, Model model) {
	        Categorie categorie = vp.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid categorie Id:" + id));
	        vp.delete(categorie);
	       
	        return "categorieList";
	    }


	
	}



