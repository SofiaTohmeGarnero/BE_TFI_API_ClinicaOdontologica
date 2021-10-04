package com.example.ProyectoIntegradorClinica.service;

import com.example.ProyectoIntegradorClinica.dto.TurnoDto;

import java.util.Set;

public interface ITurnoService extends IService<TurnoDto> {
    Set<TurnoDto> consultarTurnosProximaSemana();
}
