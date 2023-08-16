package com.servidorpipoca.pipocaagil.repositories;

import com.servidorpipoca.pipocaagil.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
