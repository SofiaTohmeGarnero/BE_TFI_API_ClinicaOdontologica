package com.example.ProyectoIntegradorClinica.service.imp;

import com.example.ProyectoIntegradorClinica.Exception.BadRequestException;
import com.example.ProyectoIntegradorClinica.Exception.ResourceNotFoundException;
import com.example.ProyectoIntegradorClinica.dto.OdontologoDto;
import com.example.ProyectoIntegradorClinica.dto.PacienteDto;
import com.example.ProyectoIntegradorClinica.dto.TurnoDto;
import com.example.ProyectoIntegradorClinica.persistence.entities.Turno;
import com.example.ProyectoIntegradorClinica.persistence.repository.ITurnoRepository;
import com.example.ProyectoIntegradorClinica.service.IService;
import com.example.ProyectoIntegradorClinica.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("TurnoService")
public class TurnoService implements ITurnoService {

    @Autowired
    ITurnoRepository repository;

    @Autowired
    @Qualifier("PacienteService")
    IService<PacienteDto> ps;

    @Autowired
    @Qualifier("OdontologoService")
    IService<OdontologoDto> os;

    @Override
    public TurnoDto buscar(Integer id) throws ResourceNotFoundException {
        if(repository.existsById(id)){
            return new TurnoDto(repository.getById(id));
        } else {
            throw new ResourceNotFoundException("No se encontró el turno con id " + id);
        }
    }

    @Override
    public TurnoDto crear(TurnoDto turnoDto) throws ResourceNotFoundException, BadRequestException{
        Integer pacienteId = turnoDto.getPaciente().getId();
        Integer odontologoId = turnoDto.getOdontologo().getId();

        if(this.verficarExistencia(pacienteId,odontologoId)){
            if(this.verificarDisponibilidad(turnoDto)) {
                turnoDto.setPaciente(ps.buscar(pacienteId));
                turnoDto.setOdontologo(os.buscar(odontologoId));
                Turno turnoGuardado = repository.save(turnoDto.toEntity());
                turnoDto.setId(turnoGuardado.getId());

                return turnoDto;
            }else{
                throw new BadRequestException("El odontólogo y/o el paciente ya tienen un turno agendado para ese día, en un intervalo de 10 minutos con respecto al horario del nuevo turno");
            }
        }else{
            throw new BadRequestException(" El odontólogo y/o el paciente no existen");
        }
    }

    private boolean verficarExistencia(Integer pacienteId, Integer odontologoId ) throws ResourceNotFoundException, BadRequestException{

        return (ps.buscar(pacienteId) != null && os.buscar(odontologoId) !=null);

    }

    private boolean verificarDisponibilidad(TurnoDto turnoNuevo){
        LocalDateTime fechaTurnoNuevo = turnoNuevo.getFecha();
        boolean response = true;

        for (TurnoDto t: this.consultarTodos()){
            if(t.getOdontologo().getId().compareTo(turnoNuevo.getOdontologo().getId()) == 0
            || t.getPaciente().getId().compareTo(turnoNuevo.getPaciente().getId()) == 0) {
                LocalDateTime fechaDesde = t.getFecha().minusMinutes(5);
                LocalDateTime fechaHasta = t.getFecha().plusMinutes(5);

                if (fechaTurnoNuevo.isAfter(fechaDesde) && fechaTurnoNuevo.isBefore(fechaHasta)) {
                    response = false;
                    break;
                }
            }

        }

        return response;
    }

    private boolean verificarDisponibilidadOdontologo(TurnoDto turnoNuevo){
        LocalDateTime fechaTurnoNuevo = turnoNuevo.getFecha();
        boolean response = true;

        for (TurnoDto t: this.consultarTodos()){
            if(t.getOdontologo().getId().compareTo(turnoNuevo.getOdontologo().getId()) == 0 ){
                LocalDateTime fechaDesde = t.getFecha().minusMinutes(15);
                LocalDateTime fechaHasta = t.getFecha().plusMinutes(15);

                if(fechaTurnoNuevo.isAfter(fechaDesde) && fechaTurnoNuevo.isBefore(fechaHasta)){
                    response = false;
                    break;
                }
            }
        }

        return response;
    }


    @Override
    public TurnoDto actualizar(TurnoDto turnoDto) throws ResourceNotFoundException, BadRequestException {

        Integer pacienteId = turnoDto.getPaciente().getId();
        Integer odontologoId = turnoDto.getOdontologo().getId();

        if(this.verficarExistencia(pacienteId,odontologoId)){
            if(this.verificarDisponibilidadOdontologo(turnoDto)) {
                turnoDto.setPaciente(ps.buscar(pacienteId));
                turnoDto.setOdontologo(os.buscar(odontologoId));
                repository.save(turnoDto.toEntity());
                return turnoDto;
            }else{
                throw new BadRequestException("El odontólogo ya tiene un turno agendado para ese día, en un intervalo de 30 minutos con respecto al horario del nuevo turno");
            }
        }else{
            throw new BadRequestException("El odontólogo y/o el paciente no existen");
        }
    }



    @Override
    public void eliminar(Integer id) throws ResourceNotFoundException {
        if(repository.existsById(id)){
            repository.delete(repository.getById(id));
        } else {
            throw new ResourceNotFoundException("No se encontró el turno con id " + id);
        }
    }

    @Override
    public Set<TurnoDto> consultarTodos() {
        Set<TurnoDto> agendaTurnos = new HashSet<>();

        for(Turno t : repository.findAll()){
            agendaTurnos.add(new TurnoDto(t));
        }

        return agendaTurnos;
    }

    public Set<TurnoDto> consultarTurnosProximaSemana() {
        Set<TurnoDto> turnosProximaSemana = new HashSet<>();
        for (Turno t: repository.findAll()){
            if(t.getFecha().isBefore(LocalDateTime.now().plusWeeks(1)) && t.getFecha().isAfter(LocalDateTime.now())) {
                turnosProximaSemana.add(new TurnoDto(t));
            }
        }
        return turnosProximaSemana;
    }

}
