package br.com.appcrud.crud.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.appcrud.crud.entity.UserEntity;
import br.com.appcrud.crud.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
	private BCryptPasswordEncoder passwordEncoder(){	 
		return new BCryptPasswordEncoder();
	}

    public List<UserEntity> findall() {
        return userRepository.findAll();
    }

    @Transactional
    public UserEntity save(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder().encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }
       
    
    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id);
    }
    
    @Transactional
    public void delete(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }   
    
    public UserEntity findByUsername(String username){
    	return userRepository.findByUsername(username)
    			.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado! " + username));
    }
    
    
    public UserEntity getByEmail(String email){
    	return userRepository.findByEmail(email)
    			.orElseThrow(() -> new EntityNotFoundException("Email não encontrado! " + email));
    }
    
    public Optional<UserEntity> findByEmail(String email){
    	return userRepository.findByEmail(email);
    }

    
}
