package com.servidorpipoca.pipocaagil.models.dto;

import java.time.LocalDate;

public record UserCreateDTO(String name, String email,String password, String phone, LocalDate dateBirth) {
}
