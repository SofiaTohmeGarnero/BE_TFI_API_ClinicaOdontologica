package com.example.ProyectoIntegradorClinica.persistence.repository.auth;

import com.example.ProyectoIntegradorClinica.persistence.entities.auth.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("from Usuario u where u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);
}
