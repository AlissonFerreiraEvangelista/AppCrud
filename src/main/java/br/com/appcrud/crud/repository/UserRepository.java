package br.com.appcrud.crud.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.appcrud.crud.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    public Optional<UserEntity> findByUsername(String username);
    
    public Optional<UserEntity> findByEmail(String email);
    
}
