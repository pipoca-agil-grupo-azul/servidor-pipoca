package com.servidorpipoca.pipocaagil.services;

import com.servidorpipoca.pipocaagil.models.User;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import com.servidorpipoca.pipocaagil.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException("Usuário não encontrado" + username);
        }
        return new UserSpringSecurity(user.getId(),user.getEmail(),user.getPassword());
    }
}

