package com.example.ProyectoIntegradorClinica.persistence.repository;

import com.example.ProyectoIntegradorClinica.persistence.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {
}
