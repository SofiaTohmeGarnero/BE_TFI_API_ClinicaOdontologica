package com.example.ProyectoIntegradorClinica.dto;

import com.example.ProyectoIntegradorClinica.persistence.entities.Odontologo;
import com.example.ProyectoIntegradorClinica.persistence.entities.Paciente;
import com.example.ProyectoIntegradorClinica.persistence.entities.Turno;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TurnoDto {

    private Integer id;
    private LocalDateTime fecha;
    private PacienteDto paciente;
    private OdontologoDto odontologo;

    public TurnoDto() {}

    public TurnoDto(Turno t) {
        id= t.getId();
        fecha= t.getFecha();
        paciente= new PacienteDto(t.getPaciente());
        odontologo= new OdontologoDto(t.getOdontologo());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public PacienteDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDto paciente) {
        this.paciente = paciente;
    }

    public OdontologoDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoDto odontologo) {
        this.odontologo = odontologo;
    }

    public Turno toEntity() {
        Turno entity = new Turno();

        entity.setId(id);
        entity.setFecha(fecha);
        entity.setPaciente(paciente.toEntity());
        entity.setOdontologo(odontologo.toEntity());

        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TurnoDto turnoDto = (TurnoDto) o;
        return (fecha.equals(turnoDto.getFecha()) && odontologo.getId() == turnoDto.getOdontologo().getId() && paciente.getId() == turnoDto.getPaciente().getId());
    }

}
