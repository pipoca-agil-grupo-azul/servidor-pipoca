package com.servidorpipoca.pipocaagil.services;

import com.servidorpipoca.pipocaagil.models.User;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional
    public User create(User user) {
        user.setId(null);
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public User update(User user) {
        User newUser = findById(user.getId());
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
