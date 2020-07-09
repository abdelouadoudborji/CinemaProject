package com.borji.cinema.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.borji.cinema.entities.Ville;

@RepositoryRestResource
@CrossOrigin("*")
public interface VilleRepository extends CrudRepository<Ville, Long>{
	public Page<Ville> findByNameContains(String kw, Pageable p);


}
