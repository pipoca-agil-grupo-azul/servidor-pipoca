package com.servidorpipoca.pipocaagil.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.servidorpipoca.pipocaagil.models.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Getter
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

    @Column(name = "role", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole role;

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            this.role = UserRole.USER;
        }
        return List.of(new SimpleGrantedAuthority(this.role.getRole()));
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isEnabled() {
        return true;
    }

}