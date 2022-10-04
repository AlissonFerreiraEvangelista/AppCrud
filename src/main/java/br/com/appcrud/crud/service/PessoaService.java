package br.com.appcrud.crud.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.appcrud.crud.entity.PessoaEntity;
import br.com.appcrud.crud.repository.PessoaRepository;



@Service
public class PessoaService {
    

    @Autowired
    PessoaRepository crudRepositorio;

    public List<PessoaEntity> findall() {
        return crudRepositorio.findAll();
    }

    @Transactional
    public PessoaEntity save(PessoaEntity crudEntidade) {
        return crudRepositorio.save(crudEntidade);
    }
       
    
    public Optional<PessoaEntity> findById(UUID id) {
        return crudRepositorio.findById(id);
    }
    
    @Transactional
    public void delete(PessoaEntity crudEntidade) {
        crudRepositorio.delete(crudEntidade);
    }   



}
