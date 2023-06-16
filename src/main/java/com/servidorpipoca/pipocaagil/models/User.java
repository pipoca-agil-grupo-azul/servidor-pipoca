package com.servidorpipoca.pipocaagil.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = User.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public static final String TABLE_NAME = "users";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @Size(min = 8, max = 100)
    @Pattern(regexp = "^[a-zA-Z]+(\\s+[a-zA-Z]+)+$", message = "Nome com formato inválido, precisa conter nome e sobrenome")
    @NotBlank
    private String name;

    @Column(name = "password", length = 60, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 60)
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Senha inválida, precisa conter 1 letra maiúscula, 1 letra minúscula, 1 número e 1 caractér especial")
    @NotBlank
    private String password;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @Email(message = "Email inválido", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotBlank
    private String email;

    @Column(name = "date_birth", nullable = false)
    @NotNull
    @Past
    private LocalDate dateBirth;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }
}
