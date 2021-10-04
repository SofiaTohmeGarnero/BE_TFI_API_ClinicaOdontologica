package com.example.ProyectoIntegradorClinica.service.imp;

import com.example.ProyectoIntegradorClinica.Exception.BadRequestException;
import com.example.ProyectoIntegradorClinica.Exception.ResourceNotFoundException;
import com.example.ProyectoIntegradorClinica.dto.PacienteDto;
import com.example.ProyectoIntegradorClinica.persistence.entities.Paciente;
import com.example.ProyectoIntegradorClinica.persistence.repository.IPacienteRepository;
import com.example.ProyectoIntegradorClinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("PacienteService")
public class PacienteService implements IService<PacienteDto> {

    @Autowired
    IPacienteRepository repository;

    @Override
    public PacienteDto buscar(Integer id) throws ResourceNotFoundException {
        if(repository.existsById(id)){
            return new PacienteDto(repository.getById(id));
        } else {
            throw new ResourceNotFoundException("No se encontró el paciente con id " + id);
        }
    }

    @Override
    public PacienteDto crear(PacienteDto pacienteDto) throws ResourceNotFoundException, BadRequestException{
        pacienteDto.setFechaIngreso(LocalDate.now());
        Paciente pacienteGuardado = repository.save(pacienteDto.toEntity());
        pacienteDto.setId(pacienteGuardado.getId());
        pacienteDto.getDomicilio().setId(pacienteGuardado.getDomicilio().getId());
        return pacienteDto;
    }

    @Override
    public PacienteDto actualizar(PacienteDto pacienteDto) throws ResourceNotFoundException, BadRequestException{
        if(repository.existsById(pacienteDto.getId())){
            Paciente pacienteEnBD =  repository.getById(pacienteDto.getId());
            pacienteDto.setFechaIngreso(pacienteEnBD.getFechaIngreso());
            pacienteDto.getDomicilio().setId(pacienteEnBD.getDomicilio().getId());
            repository.save(pacienteDto.toEntity());
            return pacienteDto;
        }else{
            throw new ResourceNotFoundException("No existe el paciente");
        }

    }

    @Override
    public void eliminar(Integer id) throws ResourceNotFoundException{

        if(repository.existsById(id)){
            repository.delete(repository.getById(id));
        } else {
            throw new ResourceNotFoundException("No se encontró el paciente con id " + id);
        }
    }

    @Override
    public Set<PacienteDto> consultarTodos() {
        Set<PacienteDto> listadoPacientes = new HashSet<>();

        for(Paciente p: repository.findAll()){
            listadoPacientes.add(new PacienteDto(p));
        }
        return listadoPacientes;
    }
}
