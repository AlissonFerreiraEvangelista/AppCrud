package br.com.appcrud.crud.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.appcrud.crud.entity.UserEntity;
import br.com.appcrud.crud.repository.UserRepository;
import br.com.appcrud.crud.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("auth")
public class UserController {
    
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/findall")
    @ApiOperation(value = "Retorna uma lista de usuários")
    public ResponseEntity<List<UserEntity>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findall());
    }

    @PostMapping("/register")
    @ApiOperation(value = "Cadastra um usuário")
    public ResponseEntity<Object> registerUser (@RequestBody UserEntity userEntity){
       Optional<UserEntity> user = userRepository.findByUsername(userEntity.getUsername());
        if (!user.isPresent()) {     
            return ResponseEntity.status(HttpStatus.OK).body(userService.save(userEntity));
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User already exists!");
    }

}
