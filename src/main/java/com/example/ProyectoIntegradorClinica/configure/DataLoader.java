package com.example.ProyectoIntegradorClinica.configure;

import com.example.ProyectoIntegradorClinica.persistence.entities.auth.Rol;
import com.example.ProyectoIntegradorClinica.persistence.entities.auth.Usuario;
import com.example.ProyectoIntegradorClinica.persistence.repository.auth.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements ApplicationRunner {

    private final IUsuarioRepository repository;

    @Autowired
    public DataLoader(IUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashedPassword1 = passwordEncoder.encode("adminpass");
        String hashedPassword2 = passwordEncoder.encode("userpass");

        repository.save(new Usuario("SoyAdmin", "soyadmin@gmail.com", hashedPassword1, Rol.ADMIN));
        repository.save(new Usuario("SoyUser", "soyuser@gmail.com", hashedPassword2, Rol.USER));

    }
}
