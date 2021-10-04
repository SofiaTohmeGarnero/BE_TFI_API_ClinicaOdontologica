package com.example.ProyectoIntegradorClinica.service;

import com.example.ProyectoIntegradorClinica.Exception.BadRequestException;
import com.example.ProyectoIntegradorClinica.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface IService<T> {
    T buscar(Integer id) throws ResourceNotFoundException;
    T crear(T t) throws ResourceNotFoundException, BadRequestException;
    T actualizar(T t) throws ResourceNotFoundException, BadRequestException;
    void eliminar(Integer id) throws ResourceNotFoundException;
    Set<T> consultarTodos();


}
