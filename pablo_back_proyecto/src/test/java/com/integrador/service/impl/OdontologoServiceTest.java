package com.integrador.service.impl;

import com.integrador.dto.OdontologoDto;
import com.integrador.entity.Odontologo;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @BeforeEach
    void setup() {
        // Configurar el estado inicial para las pruebas
    }

    @AfterEach
    void cleanup() {
        // Limpiar o restablecer el estado después de cada prueba
    }

    @Test
    @Order(1)
    void deberiaInsertarUnOdontologo() throws BadRequestException {
        Odontologo odontologoAInsertar = new Odontologo("MAT1585", "Pablo", "Morelli");
        OdontologoDto odontologoDto = odontologoService.registrarOdontologo(odontologoAInsertar);

        assertNotNull(odontologoDto);
        assertNotNull(odontologoDto.getId());
    }

    @Test
    @Order(2)
    void noDeberiaRegistrarOdontologoSinMatricula() {
        Odontologo odontologo = new Odontologo("1234", "Marco", "Polo");
        assertTrue(odontologo.getMatricula() != null);
    }

    @Test
    @Order(3)
    void deberiaListarUnSoloOdontologo() throws ResourceNotFoundException {
        List<OdontologoDto> odontologoDtos = odontologoService.listarOdontologos();
        assertEquals(1, odontologoDtos.size());
    }

    @Test
    @Order(4)
    void deberiaEliminarElOdontologo() throws BadRequestException, ResourceNotFoundException {
        odontologoService.eliminarOdontologo(1L);
    }
}
