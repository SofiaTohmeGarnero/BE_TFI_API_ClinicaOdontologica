package com.example.ProyectoIntegradorClinica.service.imp;

import com.example.ProyectoIntegradorClinica.Exception.BadRequestException;
import com.example.ProyectoIntegradorClinica.Exception.ResourceNotFoundException;
import com.example.ProyectoIntegradorClinica.dto.OdontologoDto;
import com.example.ProyectoIntegradorClinica.persistence.entities.Odontologo;
import com.example.ProyectoIntegradorClinica.persistence.entities.Paciente;
import com.example.ProyectoIntegradorClinica.persistence.repository.IOdontologoRepository;
import com.example.ProyectoIntegradorClinica.persistence.repository.IPacienteRepository;
import com.example.ProyectoIntegradorClinica.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("OdontologoService")
@Transactional
public class OdontologoService implements IService<OdontologoDto> {

    @Autowired
    IOdontologoRepository repository;

    @Override
    public OdontologoDto buscar(Integer id) throws ResourceNotFoundException {
        if(repository.existsById(id)){
            return new OdontologoDto(repository.getById(id));
        } else {
            throw new ResourceNotFoundException("No se encontró el odontólogo con id " + id);
        }

    }

    @Override
    public OdontologoDto crear(OdontologoDto odontologoDto) throws ResourceNotFoundException, BadRequestException{
        Odontologo o = odontologoDto.toEntity();
        repository.save(o);
        odontologoDto.setId(o.getId());
        return odontologoDto;
    }

    @Override
    public OdontologoDto actualizar(OdontologoDto odontologoDto) throws ResourceNotFoundException, BadRequestException{

        if(repository.existsById(odontologoDto.getId())){
            repository.save(odontologoDto.toEntity());
            return odontologoDto;
        }else{
            throw new ResourceNotFoundException("No existe el paciente");
        }
    }

    @Override
    public void eliminar(Integer id) throws ResourceNotFoundException{

        if(repository.existsById(id)){
            repository.delete(repository.getById(id));
        } else {
            throw new ResourceNotFoundException("No se encontró el odontólogo con id " + id);
        }
    }

    @Override
    public Set<OdontologoDto> consultarTodos() {
        Set<OdontologoDto> staffOdontologos = new HashSet<>();

        for(Odontologo o: repository.findAll()){
            staffOdontologos.add(new OdontologoDto(o));
        }
        return staffOdontologos;
    }
}
