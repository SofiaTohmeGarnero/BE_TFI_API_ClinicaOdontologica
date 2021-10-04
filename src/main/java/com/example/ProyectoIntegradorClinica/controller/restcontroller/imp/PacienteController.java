package com.example.ProyectoIntegradorClinica.controller.restcontroller.imp;

import com.example.ProyectoIntegradorClinica.Exception.BadRequestException;
import com.example.ProyectoIntegradorClinica.Exception.ResourceNotFoundException;
import com.example.ProyectoIntegradorClinica.controller.restcontroller.IController;
import com.example.ProyectoIntegradorClinica.dto.PacienteDto;
import com.example.ProyectoIntegradorClinica.service.IService;
import com.example.ProyectoIntegradorClinica.service.imp.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements IController<PacienteDto> {

    @Autowired
    @Qualifier("PacienteService")
    IService<PacienteDto> ps;

    private final Logger logger = Logger.getLogger(PacienteController.class);

    @Override
    @GetMapping("/buscarId/{id}")
    public ResponseEntity<PacienteDto> buscarPorId(@PathVariable("id") Integer id) throws ResourceNotFoundException, BadRequestException {

        logger.debug("Iniciando el método 'buscarPorId' para pacientes ");

        if(id >= 0){
            return ResponseEntity.ok(ps.buscar(id));
        }else{
            throw new BadRequestException("El id del paciente no puede ser un número negativo");
        }

    }

    @Override
    @PostMapping("/nuevo")
    public ResponseEntity<PacienteDto> crearNuevo(@RequestBody PacienteDto paciente) throws ResourceNotFoundException, BadRequestException{

        logger.debug("Iniciando el método 'crearNuevo' para pacientes ");

        if(paciente.getNombre() != null && paciente.getApellido() != null && paciente.getDni() != null && paciente.getDomicilio() != null ){
            return ResponseEntity.ok(ps.crear(paciente));
        }else{
            throw new BadRequestException("No se puede crear un paciente con parametros en null");
        }
    }

    @Override
    @PutMapping("/actualizar")
    public ResponseEntity<PacienteDto> actualizar(@RequestBody PacienteDto paciente) throws ResourceNotFoundException, BadRequestException{

        logger.debug("Iniciando el método 'actualizar' para pacientes ");

        if(paciente.getId() != null){
            return ResponseEntity.ok(ps.actualizar(paciente));
        }else{
            throw new BadRequestException("No se puede actualizar un paciente con id null");
        }
    }

    @Override
    @DeleteMapping("/eliminarId/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable("id") Integer id) throws ResourceNotFoundException, BadRequestException{

        logger.debug("Iniciando el método 'eliminarPorId' para pacientes ");

        if(id >= 0){
            ps.eliminar(id);
            return ResponseEntity.ok("Se eliminó el paciente con id "+ id);
        }else{
            throw new BadRequestException("El id del paciente no puede ser un número negativo");
        }
    }

    @Override
    @GetMapping("/todos")
    public ResponseEntity<Set<PacienteDto>> consultarTodos(){

        logger.debug("Iniciando el método 'consultarTodos' para pacientes ");

        return ResponseEntity.ok(ps.consultarTodos());
    }
}
