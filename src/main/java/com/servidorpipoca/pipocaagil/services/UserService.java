package com.servidorpipoca.pipocaagil.services;

import com.servidorpipoca.pipocaagil.models.User;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new EntityNotFoundException(
                "Usuário não encontrado! Id: " + id));
    }

    @Transactional
    public User create(@Valid User user) {
        user.setId(null);
        nameValidator(user.getName());
        passwordValidator(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        emailValidator(user.getEmail());
        ageValidator(user.getDateBirth());
        return userRepository.save(user);
    }

    @Transactional
    public User update(@Valid User user) {
        User newUser = findById(user.getId());
        passwordValidator(user.getPassword());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        emailValidator(user.getEmail());
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

    public void nameValidator(String name) {
        String regex = "^[A-Za-zÇç]+(?: [A-Za-zÇç]+)+$";

        if (!Pattern.matches(regex, name)) {
            throw new IllegalArgumentException("O nome não é válido. Certifique-se de que ele tenha dois ou mais nomes separados por espaço e não contenha números ou caracteres especiais");
        }
    }

    public void passwordValidator(String password) {
        String regex = "^(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$";

        if (!Pattern.matches(regex, password)) {
            throw new IllegalArgumentException("Senha inválida, precisa conter 1 carácter especial, maiúsculo, minúsculo e 1 número");
        }
    }

    public void emailValidator(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        if (!Pattern.matches(regex, email)) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já registrado, tente outro");
        }
    }

    public void ageValidator(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        LocalDate minimumDateOfBirth = currentDate.minusYears(18);

        if (date.isAfter(currentDate)){
            throw new IllegalArgumentException("Data inválida. Digite uma data existente");
        }

        if (date.isAfter(minimumDateOfBirth)) {
            throw new IllegalArgumentException("Idade não permitida. O usuário precisa ter no mínimo 18 anos");
        }
    }
}
