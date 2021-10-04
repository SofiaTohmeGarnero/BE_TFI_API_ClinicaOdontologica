package com.example.ProyectoIntegradorClinica.persistence.entities.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Setter
@Getter
public class Usuario {


    @Id
    @GeneratedValue
    private Integer id;
    private String userName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol userRol;

    public Usuario(){

    }
    public Usuario(String userName, String email, String password, Rol userRol) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userRol = userRol;
    }
}
