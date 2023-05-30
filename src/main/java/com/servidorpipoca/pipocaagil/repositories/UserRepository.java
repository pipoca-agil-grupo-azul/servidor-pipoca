package com.servidorpipoca.pipocaagil.repositories;

import com.servidorpipoca.pipocaagil.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
