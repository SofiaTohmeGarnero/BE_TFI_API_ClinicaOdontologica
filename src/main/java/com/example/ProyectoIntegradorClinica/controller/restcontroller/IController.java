package com.example.ProyectoIntegradorClinica.controller.restcontroller;

import com.example.ProyectoIntegradorClinica.Exception.BadRequestException;
import com.example.ProyectoIntegradorClinica.Exception.ResourceNotFoundException;
import com.example.ProyectoIntegradorClinica.dto.PacienteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

public interface IController<T> {

    ResponseEntity<T> buscarPorId(@PathVariable("id") Integer id) throws ResourceNotFoundException, BadRequestException;

    ResponseEntity<T> crearNuevo(@RequestBody T t) throws ResourceNotFoundException, BadRequestException;

    ResponseEntity<T> actualizar(@RequestBody T t) throws ResourceNotFoundException, BadRequestException;

    ResponseEntity<String> eliminarPorId(@PathVariable("id") Integer id) throws ResourceNotFoundException, BadRequestException;

    ResponseEntity<Set<T>> consultarTodos();
}
