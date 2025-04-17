package br.com.devjmcn.backend_projeto_temperatura.model.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
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
    @NotBlank
    String unit;

    public UserEntity(@NotNull @NotBlank String name, @NotNull @NotBlank String email, String passHash, @NotNull @NotBlank String unit) {
        this.name = name;
        this.email = email;
        this.password = passHash;
        this.unit = unit;
    }
}
