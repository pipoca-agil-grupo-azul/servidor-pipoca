package com.servidorpipoca.pipocaagil.models.dto;

import java.time.LocalDate;

public record UserCreateDTO(String name, String password, String email, LocalDate dateBirth) {
}
