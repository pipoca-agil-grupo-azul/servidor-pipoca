package com.servidorpipoca.pipocaagil.models.dto;

import jakarta.validation.Valid;

public record UserUpdateDTO(@Valid Long id, String email, String password) {
}
