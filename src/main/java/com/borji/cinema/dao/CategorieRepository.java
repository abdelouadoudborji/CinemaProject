package com.borji.cinema.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.borji.cinema.entities.Categorie;
import com.borji.cinema.entities.Ville;

@RepositoryRestResource
public interface CategorieRepository extends JpaRepository<Categorie, Long>{
	public Page<Categorie> findByNameContains(String kw, Pageable p);
}
