package com.example.ProyectoIntegradorClinica.service.imp;

import com.example.ProyectoIntegradorClinica.Exception.BadRequestException;
import com.example.ProyectoIntegradorClinica.Exception.ResourceNotFoundException;
import com.example.ProyectoIntegradorClinica.dto.OdontologoDto;
import com.example.ProyectoIntegradorClinica.persistence.entities.Odontologo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServiceTest {

    @Autowired
    OdontologoService os;

    private OdontologoDto oDto;

    @BeforeEach
    void setUp(){
        oDto = new OdontologoDto();

        oDto.setApellido("Cano");
        oDto.setNombre("Mariano");
        oDto.setMatricula("MN 1234");
    }

    @Test
    @Order(1)
    void crear() throws BadRequestException, ResourceNotFoundException {
        OdontologoDto rtaObtenida = os.crear(oDto);

        Assertions.assertTrue(rtaObtenida.getId() != null);

    }

    @Test
    @Order(2)
    void buscar_lanzarExcepcion() throws ResourceNotFoundException, BadRequestException {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {os.buscar(15);}, "No se encontró el odontólogo con id 15");
    }

    @Test
    @Order(3)
    void buscar() throws ResourceNotFoundException, BadRequestException {
        OdontologoDto oCreado  = os.crear(oDto);
        OdontologoDto rtaEsperada = os.buscar(oCreado.getId());
        Assertions.assertTrue(rtaEsperada != null);
    }

    @Test
    @Order(4)
    void actualizar() throws BadRequestException, ResourceNotFoundException {
        OdontologoDto o = os.crear(oDto);
        OdontologoDto oCreado = os.buscar(o.getId());
        o.setNombre("Mirko");
        OdontologoDto oActualizado = os.actualizar(o);

        Assertions.assertNotEquals(oCreado, oActualizado);
        Assertions.assertEquals(oCreado.getId(), oActualizado.getId());
    }

    @Test
    @Order(5)
    void eliminar() throws BadRequestException, ResourceNotFoundException {
        OdontologoDto oCreado = os.crear(oDto);
        os.eliminar(oCreado.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {os.buscar(oCreado.getId());});
    }

    @Test
    @Order(6)
    void consultarTodos() throws BadRequestException, ResourceNotFoundException {
        OdontologoDto oCreado = os.crear(oDto);
        Set<OdontologoDto> rtaObtenida = os.consultarTodos();

        Assertions.assertFalse(rtaObtenida.size() == 0);

    }
}