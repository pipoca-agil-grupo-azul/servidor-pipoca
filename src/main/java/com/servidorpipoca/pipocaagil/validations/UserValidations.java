package com.servidorpipoca.pipocaagil.validations;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.servidorpipoca.pipocaagil.repositories.UserRepository;

public class UserValidations {

    @Autowired
    private UserRepository userRepository;

    public UserValidations(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void nameValidator(String name) {
        String regex = "^[A-Za-zÇç]+(?: [A-Za-zÇç]+)+$";

        if (!Pattern.matches(regex, name)) {
            throw new IllegalArgumentException(
                    "O nome não é válido. Certifique-se de que ele tenha dois ou mais nomes separados por espaço e não contenha números ou caracteres especiais");
        }
    }

    public void passwordValidator(String password) {
        String regex = "^(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$";

        if (!Pattern.matches(regex, password)) {
            throw new IllegalArgumentException(
                    "Senha inválida, precisa conter 1 carácter especial, maiúsculo, minúsculo e 1 número");
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

        if (date.isAfter(currentDate)) {
            throw new IllegalArgumentException("Data inválida. Digite uma data existente");
        }

        if (date.isAfter(minimumDateOfBirth)) {
            throw new IllegalArgumentException("Idade não permitida. O usuário precisa ter no mínimo 18 anos");
        }
    }
}
