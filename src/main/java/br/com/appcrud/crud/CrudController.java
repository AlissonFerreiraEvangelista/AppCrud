package br.com.appcrud.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;





@Controller
@ResponseBody
@CrossOrigin
@RequestMapping("crud")
public class CrudController {
    @Autowired CrudRepositorio crudeRepositorio;

   
    @GetMapping("/findall")
    public List<CrudEntidade> getListarConsultas() {
        List<CrudEntidade> lista = crudeRepositorio.findAll();
        return lista;
    }

    @PostMapping("/register")
    public CrudEntidade postCadastro(@RequestBody CrudEntidade novaConsulta) {
        CrudEntidade consultaSalva = crudeRepositorio.save(novaConsulta);
        return consultaSalva;
    }

    @GetMapping("/findone/{id}")
    public CrudEntidade getConsulta(@PathVariable Long id){
        CrudEntidade consulta = crudeRepositorio.findById(id).get();
        return consulta;

    }
    @GetMapping("/delete/{id}")
    public void getDeletar(@PathVariable Long id){
        crudeRepositorio.deleteById(id);

    }
   
    

    
}
