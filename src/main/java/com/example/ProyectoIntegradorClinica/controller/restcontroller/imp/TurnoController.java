package com.example.ProyectoIntegradorClinica.controller.restcontroller.imp;

import com.example.ProyectoIntegradorClinica.Exception.BadRequestException;
import com.example.ProyectoIntegradorClinica.Exception.ResourceNotFoundException;
import com.example.ProyectoIntegradorClinica.controller.restcontroller.IController;
import com.example.ProyectoIntegradorClinica.dto.TurnoDto;
import com.example.ProyectoIntegradorClinica.service.IService;
import com.example.ProyectoIntegradorClinica.service.ITurnoService;
import com.example.ProyectoIntegradorClinica.service.imp.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/turnos")
public class TurnoController implements IController<TurnoDto> {

    @Autowired
    @Qualifier("TurnoService")
    ITurnoService ts;

    private final Logger logger = Logger.getLogger(TurnoController.class);

    @Override
    @GetMapping("/buscarId/{id}")
    public ResponseEntity<TurnoDto> buscarPorId(@PathVariable("id") Integer id) throws ResourceNotFoundException, BadRequestException {

        logger.debug("Iniciando el método 'buscarPorId' para turnos ");

        if(id >= 0){
            return ResponseEntity.ok(ts.buscar(id));
        }else{
            throw new BadRequestException("El id del turno no puede ser un número negativo");
        }
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/nuevo")
    public ResponseEntity<TurnoDto> crearNuevo(TurnoDto turno) throws ResourceNotFoundException, BadRequestException{
        logger.debug("Iniciando el método 'crearNuevo' para turnos ");

        if(turno.getPaciente() != null && turno.getOdontologo() != null && turno.getFecha() != null){
            return ResponseEntity.ok(ts.crear(turno));
        }else{
            throw new BadRequestException("No se puede crear un turno con parámetros en null");
        }
    }

    @Override
    @PutMapping("/actualizar")
    public ResponseEntity<TurnoDto> actualizar(TurnoDto turno) throws ResourceNotFoundException, BadRequestException{
        logger.debug("Iniciando el método 'actualizar' para turnos ");

        if(turno.getId() != null){
            return ResponseEntity.ok(ts.actualizar(turno));
        }else{
            throw new BadRequestException("No se puede actualizar un turno con id null");
        }
    }

    @Override
    @DeleteMapping("/eliminarId/{id}")
    public ResponseEntity<String> eliminarPorId(Integer id) throws ResourceNotFoundException, BadRequestException{
        logger.debug("Iniciando el método 'eliminarPorId' para turnos ");

        if(id >= 0){
            ts.eliminar(id);
            return ResponseEntity.ok("Se eliminó el turno con id "+ id);
        }else{
            throw new BadRequestException("El id del turno no puede ser un número negativo");
        }

    }

    @Override
    @GetMapping("/todos")
    public ResponseEntity<Set<TurnoDto>> consultarTodos() {

        logger.debug("Iniciando el método 'consultarTodos' para turnos ");

        return ResponseEntity.ok(ts.consultarTodos());
    }

    @GetMapping("/proximaSemana")
    public ResponseEntity<Set<TurnoDto>> consultarTurnosProximaSemana(){

        logger.debug("Iniciando el método 'consultarTurnosProximaSemana' para turnos ");

        return ResponseEntity.ok(ts.consultarTurnosProximaSemana());
    }

}
