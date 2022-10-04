package br.com.appcrud.crud.entity;


import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_PESSOA")
public class PessoaEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String nome;

    String sobreNome;
    
    LocalDate dataNascimento;
    
}
