package com.example.ProyectoIntegradorClinica.dto;

import com.example.ProyectoIntegradorClinica.persistence.entities.Domicilio;
import com.example.ProyectoIntegradorClinica.persistence.entities.Paciente;

import java.time.LocalDate;

public class PacienteDto {

    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaIngreso;
    private DomicilioDto domicilio;

    public PacienteDto(){}

    public PacienteDto(Paciente p){
        id = p.getId();
        nombre = p.getNombre();
        apellido = p.getApellido();
        dni = p.getDni();
        fechaIngreso = p.getFechaIngreso();
        domicilio = new DomicilioDto(p.getDomicilio());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public DomicilioDto getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioDto domicilio) {
        this.domicilio = domicilio;
    }

    public Paciente toEntity(){
        Paciente entity = new Paciente();

        entity.setId(id);
        entity.setApellido(apellido);
        entity.setDni(dni);
        entity.setNombre(nombre);
        entity.setFechaIngreso(fechaIngreso);
        entity.setDomicilio(domicilio.toEntity());

        return entity;
    }
}
