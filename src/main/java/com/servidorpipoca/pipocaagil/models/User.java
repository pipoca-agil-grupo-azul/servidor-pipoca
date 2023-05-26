package com.servidorpipoca.pipocaagil.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = User.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public static final String TABLE_NAME = "user";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @Size(min = 3, max = 100)
    @NotBlank
    private String username;

    @Column(name = "password", length = 60, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 60)
    @NotBlank
    private String password;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(name = "dateBirth", nullable = false)
    @NotBlank
    private LocalDate dateBirth;

}
