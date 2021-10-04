package com.example.ProyectoIntegradorClinica.service.imp.auth;

import com.example.ProyectoIntegradorClinica.persistence.entities.auth.Rol;
import com.example.ProyectoIntegradorClinica.persistence.entities.auth.Usuario;
import com.example.ProyectoIntegradorClinica.persistence.repository.auth.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService {

    private final  IUsuarioRepository repository;

    @Autowired
    public UsuarioService(IUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Usuario> usuario = repository.findByEmail(s);

        SimpleGrantedAuthority autorizacion = new SimpleGrantedAuthority(usuario.get().getUserRol().name());

        User userDatails = new User(usuario.get().getEmail(), usuario.get().getPassword(), true, true, true, true, Collections.singleton(autorizacion));

        return userDatails;
    }

    public Usuario crear(Usuario u){
        return repository.save(u);
    }

    public Set<Usuario> consultarTodos() {
        Set<Usuario> listadoUsuarios = new HashSet<>();
        for( Usuario u : repository.findAll()){
            listadoUsuarios.add(u);
        }

        return listadoUsuarios;

    }
}
