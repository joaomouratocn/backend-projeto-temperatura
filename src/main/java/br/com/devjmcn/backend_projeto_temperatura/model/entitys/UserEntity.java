package br.com.devjmcn.backend_projeto_temperatura.model.entitys;

import br.com.devjmcn.backend_projeto_temperatura.util.UserRoles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue
    UUID id;
    @NotNull
    @NotBlank
    String name;
    @NotNull
    @NotBlank
    String email;
    @NotNull
    @NotBlank
    String password;
    @NotNull
    UUID unit;
    @NotNull
    UserRoles role;

    public UserEntity(
            @NotNull @NotBlank String name,
            @NotNull @NotBlank String email,
            @NotNull @NotBlank String passHash,
            @NotNull UUID unit,
            @NotNull UserRoles role
    ) {
        this.name = name;
        this.email = email;
        this.password = passHash;
        this.unit = unit;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRoles.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
