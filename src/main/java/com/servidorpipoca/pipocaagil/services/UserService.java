package com.servidorpipoca.pipocaagil.services;

import com.servidorpipoca.pipocaagil.models.Users;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users findById(Long id) {
        Optional<Users> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + Users.class.getName()));
    }

    @Transactional
    public Users create(Users user) {
        user.setId(null);
        user = this.userRepository.save(user);
        return user;
    }

    @Transactional
    public Users update(Users user) {
        Users newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        return userRepository.save(newUser);
    }

    public void delete(Long id) {
        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível realizar a exclusão!");
        }
    }
}
