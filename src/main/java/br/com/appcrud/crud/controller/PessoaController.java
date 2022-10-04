package br.com.appcrud.crud.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import br.com.appcrud.crud.entity.PessoaEntity;
import br.com.appcrud.crud.service.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;





@RestController
@CrossOrigin
@RequestMapping("crud")
@Api(value = "API REST CRUD")
public class PessoaController {
    @Autowired
    PessoaService crudService;

   
    @GetMapping("/findall")
    @ApiOperation(value = "Retorna uma lista de todos cadastrados")
    public ResponseEntity<List<PessoaEntity>> getListarConsultas() {            
        return ResponseEntity.status(HttpStatus.OK).body(crudService.findall());
    }

    @PostMapping("/register")
    @ApiOperation(value = "Cadastra uma Pessoa")
    public ResponseEntity<PessoaEntity> postCadastro(@RequestBody PessoaEntity crudEntidade) {
        return ResponseEntity.status(HttpStatus.OK).body(crudService.save(crudEntidade));
    }

    @GetMapping("/findone/{id}")
    @ApiOperation(value = "Retorna uma Pessoa Cadastrada")
    public PessoaEntity getConsulta(@PathVariable UUID id){
        PessoaEntity consulta = crudService.findById(id).get();
        return consulta;

    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Deleta uma Pessoa")
    public ResponseEntity<Object> deletar(@PathVariable (value="id") UUID id){
        Optional<PessoaEntity> crudEntidadeOptional = crudService.findById(id);
        if(!crudEntidadeOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }else{
            crudService.delete(crudEntidadeOptional.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @PutMapping("/update")
    @ApiOperation(value = "Atualiza uma Pessoa")
    public PessoaEntity updateCadastro(@RequestBody PessoaEntity updateConsulta) {
        PessoaEntity update = crudService.save(updateConsulta);
        return update;
    }
   
    

    
}
