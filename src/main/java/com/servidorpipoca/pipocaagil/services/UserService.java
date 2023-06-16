package com.servidorpipoca.pipocaagil.services;

import com.servidorpipoca.pipocaagil.exceptions.MinimumAgeException;
import com.servidorpipoca.pipocaagil.models.User;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import com.servidorpipoca.pipocaagil.security.JwtTokenProvider;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new EntityNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()));
    }

    @Transactional
    @Validated
    public User create(@Valid User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate minimumDateOfBirth = currentDate.minusYears(18);

        if (user.getDateBirth().isAfter(minimumDateOfBirth)) {
            throw new MinimumAgeException();
        }

        user.setId(null);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return user;
    }

    @Transactional
    public User update(@Valid User user) {
        User newUser = findById(user.getId());
        newUser.setPassword(user.getPassword());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
