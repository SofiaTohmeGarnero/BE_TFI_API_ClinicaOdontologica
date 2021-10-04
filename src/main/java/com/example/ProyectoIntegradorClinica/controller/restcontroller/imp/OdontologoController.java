package com.example.ProyectoIntegradorClinica.controller.restcontroller.imp;

import com.example.ProyectoIntegradorClinica.Exception.BadRequestException;
import com.example.ProyectoIntegradorClinica.Exception.ResourceNotFoundException;
import com.example.ProyectoIntegradorClinica.controller.restcontroller.IController;
import com.example.ProyectoIntegradorClinica.dto.OdontologoDto;
import com.example.ProyectoIntegradorClinica.service.IService;
import com.example.ProyectoIntegradorClinica.service.imp.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController implements IController<OdontologoDto> {

    @Autowired
    @Qualifier("OdontologoService")
    IService<OdontologoDto> os;

    private final Logger logger = Logger.getLogger(OdontologoController.class);

    @Override
    @GetMapping("/buscarId/{id}")
    public ResponseEntity<OdontologoDto> buscarPorId(@PathVariable("id") Integer id)throws ResourceNotFoundException, BadRequestException {

        logger.debug("Iniciando el método 'buscarPorId' para odontólogos ");

        if(id >= 0){
            return ResponseEntity.ok(os.buscar(id));
        }else{
            throw new BadRequestException("El id del odontólogo no puede ser un número negativo");
        }

    }

    @Override
    @PostMapping("/nuevo")
    public ResponseEntity<OdontologoDto> crearNuevo(@RequestBody OdontologoDto odontologo)throws ResourceNotFoundException, BadRequestException{

        logger.debug("Iniciando el método 'crearNuevo' para odontólogos ");

        if(odontologo.getNombre() != null && odontologo.getApellido() != null && odontologo.getMatricula() != null){
            return ResponseEntity.ok(os.crear(odontologo));
        }else{
            throw new BadRequestException("No se puede crear un odontólogo parámetros en null");
        }
    }

    @Override
    @PutMapping("/actualizar")
    public ResponseEntity<OdontologoDto> actualizar(@RequestBody OdontologoDto odontologo)throws ResourceNotFoundException, BadRequestException{

        logger.debug("Iniciando el método 'actualizar' para odontólogos ");

        if(odontologo.getId() != null){
            return ResponseEntity.ok(os.actualizar(odontologo));
        }else{
            throw new BadRequestException("No se puede actualizar un odontólogo con id null");
        }
    }

    @Override
    @DeleteMapping("/eliminarId/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable("id") Integer id)throws ResourceNotFoundException, BadRequestException{

        logger.debug("Iniciando el método 'eliminarPorId' para odontólogos ");

        if(id >= 0){
            os.eliminar(id);
            return ResponseEntity.ok("Se eliminó el odontólogo con id "+ id);
        }else{
            throw new BadRequestException("El id del odontólogo no puede ser un número negativo");
        }

    }

    @Override
    @GetMapping("/todos")
    public ResponseEntity<Set<OdontologoDto>> consultarTodos(){

        logger.debug("Iniciando el método 'consultarTodos' para odontólogos ");

        return ResponseEntity.ok(os.consultarTodos());
    }
}
