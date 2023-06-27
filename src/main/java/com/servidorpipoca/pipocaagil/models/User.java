package com.servidorpipoca.pipocaagil.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.servidorpipoca.pipocaagil.models.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = User.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    public static final String TABLE_NAME = "users";

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @Size(min = 8, max = 100)
    @NotBlank
    private String name;

    @Column(name = "password", length = 60, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 60)
    @NotBlank
    private String password;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotBlank
    private String email;

    @Column(name = "date_birth", nullable = false)
    @NotNull
    private LocalDate dateBirth;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}