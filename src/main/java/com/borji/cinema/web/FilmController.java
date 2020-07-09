package com.borji.cinema.web;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.borji.cinema.dao.CategorieRepository;
import com.borji.cinema.dao.CinemaRepository;
import com.borji.cinema.dao.FilmRepository;
import com.borji.cinema.entities.Categorie;
import com.borji.cinema.entities.Cinema;
import com.borji.cinema.entities.Film;

import imageuploadconfig.MyUploadForm;


@Controller
@RequestMapping("/film/")
public class FilmController {
	@Autowired 
	private FilmRepository vp;
	@Autowired 
	private CategorieRepository cp;
	String name;
	
	@GetMapping("add_film")
    public String showSignUpForm(Film film, Model model,@RequestParam(name="page", defaultValue ="0" )int page,
			@RequestParam(name="size" , defaultValue ="5" )int size,
			@RequestParam (name="keyword", defaultValue ="")String nom	 ) {
		Page<Film> v=vp.findByTitreContains(nom,PageRequest.of(page,size));
		MyUploadForm myUploadForm = new MyUploadForm();
		List<Categorie> listCategorie =cp.findAll();
		model.addAttribute("listville", listCategorie);
	   model.addAttribute("myUploadForm",myUploadForm);
        return "add_film";
    }

	@GetMapping(path="/filmList")
	public String ville(Model model,@RequestParam(name="page", defaultValue ="0" )int page,
			@RequestParam(name="size" , defaultValue ="5" )int size,
			@RequestParam (name="keyword", defaultValue ="")String nom	){
		String path="..\\..\\..\\..\\images\\";
		Page<Film> v=vp.findByTitreContains(nom,PageRequest.of(page,size));
		model.addAttribute("listPatients",v.getContent());
		model.addAttribute("pages",new int[v.getTotalPages()]);
		model.addAttribute("currentpage",page);
		model.addAttribute("keyword",nom);
		model.addAttribute("path",path);
	return "filmList";
	}
	  @GetMapping("edit/{id}")
	    public String showUpdateForm(@PathVariable("id") long id, Model model
	 ) {
	        Film film = vp.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid film Id:" + id));  
	List<Categorie> v=(List<Categorie>) cp.findAll();
	String path="..\\..\\..\\images\\";
	MyUploadForm myUploadForm = new MyUploadForm();
	  model.addAttribute("myUploadForm",myUploadForm);
		model.addAttribute("listville", v);
			model.addAttribute("film", film);
			model.addAttribute("path",path);

	        return "update_film";
	    }
	  
	  @PostMapping("update/{id}")
	    public String updateStudent(@PathVariable("id") long id, @Valid Film cinema, BindingResult result,
	        Model model , @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	        if (result.hasErrors()) {
	        	cinema.setId(id);
	            return "filmList";
	        }
	        
	        cinema.setPhoto(this.doUpload( model, myUploadForm));
	       
	        vp.save(cinema);
	        
	        return "filmList";
	  }
	  
	@PostMapping("add")
    public String addVille(@Valid Film cinema, BindingResult result,  HttpServletRequest request, //
            Model model, //
            @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) {
	
       if (result.hasErrors()) {
            return "add_film";
       }
       
        
        
        cinema.setPhoto(this.doUpload( model, myUploadForm));
       vp.save(cinema);
        
        return "redirect:filmList";
    }
	 @GetMapping("delete/{id}")
	    public String deleteStudent(@PathVariable("id") long id, Model model) {
	        Film cinema = vp.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid cinema Id:" + id));
	        vp.delete(cinema);
	       
	        return "filmList";
	    }
	 private String doUpload(Model model, //
	         MyUploadForm myUploadForm) {
	 
	    String name = null;
	 
	      // Root Directory.
	      String uploadRootPath = "C:\\Users\\Abdelouadoud Borji\\Desktop\\cinema_project\\src\\main\\resources\\static\\images";
	 
	      File uploadRootDir = new File(uploadRootPath);
	      // Create directory if it not exists.
	      if (!uploadRootDir.exists()) {
	         uploadRootDir.mkdirs();
	      }
	      MultipartFile[] fileDatas = myUploadForm.getFileDatas();
	      //
	      List<File> uploadedFiles = new ArrayList<File>();
	      List<String> failedFiles = new ArrayList<String>();
	 
	      for (MultipartFile fileData : fileDatas) {
	 
	         // Client File Name
	     name = fileData.getOriginalFilename();
	        
	 
	         if (name != null && name.length() > 0) {
	            try {
	               // Create the file at server
	               File serverFile = new File(uploadRootDir + File.separator + name);
	 
	               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	               stream.write(fileData.getBytes());
	               stream.close();
	               //
	               uploadedFiles.add(serverFile);
	         
	            } catch (Exception e) {
	             
	            }
	           
	         }
	      }
	   
	      return name;
	      
	   }
	 


	
	}



