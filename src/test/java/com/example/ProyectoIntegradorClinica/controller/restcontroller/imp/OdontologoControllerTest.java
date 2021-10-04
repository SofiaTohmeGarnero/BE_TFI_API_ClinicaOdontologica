package com.example.ProyectoIntegradorClinica.controller.restcontroller.imp;

import com.example.ProyectoIntegradorClinica.dto.OdontologoDto;
import com.example.ProyectoIntegradorClinica.util.Jsons;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoControllerTest {

    @Autowired
    private MockMvc mvc;

    private final OdontologoDto od1 = new OdontologoDto("Juan", "Ramirez", "MN 1234");
    private final OdontologoDto od2 = new OdontologoDto("Jose", "Peralta", "MN 4567");
    private final OdontologoDto od3 = new OdontologoDto("Julian", "Moreno", "MN 6789");

    private final OdontologoDto od_rta2 = new OdontologoDto(2,"Jose", "Peralta", "MN 4567");

    @Test
    @Order(1)
    public void test01_crearNuevo_respuestaEsUnOk() throws Exception {
        MvcResult response = this.mvc.perform(MockMvcRequestBuilders.post("/odontologos/nuevo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Jsons.asJsonString(od1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(2)
    public void test02_crearNuevo_respuestaEsLaEsperada() throws Exception {
        MvcResult response = this.mvc.perform(MockMvcRequestBuilders.post("/odontologos/nuevo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Jsons.asJsonString(od2)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(Jsons.asJsonString(od_rta2),response.getResponse().getContentAsString());
    }

    @Test
    @Order(3)
    void test03_buscarPorId() throws Exception {
        MvcResult result =  this.mvc.perform(MockMvcRequestBuilders.post("/odontologos/nuevo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Jsons.asJsonString(od3)))
                .andReturn();

        this.mvc.perform(MockMvcRequestBuilders.get("/odontologos/buscarId/{id}", 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Julian"));
    }

    @Order(4)
    @Test
    public void test04_actualizarOdontologo() throws Exception {
        OdontologoDto respuesta = new OdontologoDto(1, "Roberto", "Leon", "AAA999");

        MvcResult response = this.mvc.perform(MockMvcRequestBuilders.put("/odontologos/actualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Jsons.asJsonString(respuesta)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(Jsons.asJsonString(respuesta), response.getResponse().getContentAsString());
    }
    @Order(5)
    @Test
    public void test05_eliminarOdontologo() throws Exception {
        String respuesta = "Se eliminó el odontólogo con id 1";
        MvcResult response = this.mvc.perform(MockMvcRequestBuilders.delete("/odontologos/eliminarId/{id}", 1))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(status().isOk()).andReturn();
        Assert.assertEquals(respuesta, response.getResponse().getContentAsString());


    }


}