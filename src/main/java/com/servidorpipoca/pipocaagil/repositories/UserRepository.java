package com.servidorpipoca.pipocaagil.repositories;

import com.servidorpipoca.pipocaagil.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    UserDetails findByName(String username);
}
