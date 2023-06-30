package com.servidorpipoca.pipocaagil.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.servidorpipoca.pipocaagil.models.User;
import com.servidorpipoca.pipocaagil.models.dto.UserCreateDTO;
import com.servidorpipoca.pipocaagil.models.dto.UserUpdateDTO;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import com.servidorpipoca.pipocaagil.validations.UserValidations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserValidations validations;

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new EntityNotFoundException(
                "Usuário não encontrado! Id: " + id));
    }

    @Transactional
    public User create(@Valid UserCreateDTO user) {
        validations.nameValidator(user.name());
        validations.passwordValidator(user.password());
        validations.emailValidator(user.email());
        validations.ageValidator(user.dateBirth());

        User newUser = new User();
        newUser.setName(user.name());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.password()));
        newUser.setEmail(user.email());
        newUser.setDateBirth(user.dateBirth());

        return userRepository.save(newUser);
    }

    @Transactional
    public User update(@Valid Long id, UserUpdateDTO user) {
        User newUser = findById(id);

        validations.emailValidator(user.email());
        validations.passwordValidator(user.password());

        newUser.setEmail(user.email());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.password()));

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
