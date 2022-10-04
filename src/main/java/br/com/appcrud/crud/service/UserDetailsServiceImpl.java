package br.com.appcrud.crud.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.appcrud.crud.entity.UserEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        UserEntity userEntity = userService.findByUsername(username);
        if(userEntity == null) {
        	throw new UsernameNotFoundException(username);
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(),userEntity.isEnabled() ,true, true,true, new ArrayList<>());
    }
    
}
