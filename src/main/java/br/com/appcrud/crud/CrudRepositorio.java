package br.com.appcrud.crud;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface CrudRepositorio extends CrudRepository<CrudEntidade, Long> {
    List<CrudEntidade>findAll();
    
}
