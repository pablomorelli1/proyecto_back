package com.integrador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.integrador.dto.OdontologoDto;
import com.integrador.entity.Odontologo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OdontologoControllerTest {

    @Autowired
    private OdontologoController odontologoController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologo() throws Exception {
        Odontologo odontologo = new Odontologo("MAT1585","Pablo", "Morelli");
        OdontologoDto odontologoDto = new OdontologoDto(1L,"MAT1585","Pablo","Morelli");

        ObjectWriter writer = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false).writer();
        String payload = writer.writeValueAsString(odontologo);
        String expectedResponse = writer.writeValueAsString(odontologoDto);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos/registrar")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        Assertions.assertEquals(expectedResponse, response.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    void deberiaEncontrarOdontologoConId1PorApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}", 1))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula").value("MAT1585"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Morelli"));
    }
}
