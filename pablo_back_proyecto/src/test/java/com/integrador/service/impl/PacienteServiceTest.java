package com.integrador.service.impl;

import com.integrador.dto.PacienteDto;
import com.integrador.entity.Domicilio;
import com.integrador.entity.Paciente;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteServiceTest {

    @Autowired
    PacienteService pacienteService;

    private static final String NOMBRE = "Juan";
    private static final String APELLIDO = "Perez";
    private static final String DNI = "30547842";
    private static final LocalDate FECHA_NACIMIENTO = LocalDate.of(2020, 10, 10);
    private static final Domicilio DOMICILIO = new Domicilio("Las Heras", 547, "Mar del Plata", "Buenos Aires");

    @Test
    @Order(1)
    void deberiaRegistrarUnPaciente() throws BadRequestException {
        Paciente pacienteARegistrar = new Paciente(NOMBRE, APELLIDO, DNI, FECHA_NACIMIENTO, DOMICILIO);
        PacienteDto pacienteDto = pacienteService.guardarPaciente(pacienteARegistrar);

        assertNotNull(pacienteDto);
        assertNotNull(pacienteDto.getId());
    }

    @Test
    @Order(2)
    void deberiaListarPacientes() throws ResourceNotFoundException {
        List<PacienteDto> pacienteDtos = pacienteService.listarPacientes();
        assertTrue(pacienteDtos.size() > 0);
    }

    @Test
    @Order(3)
    void deberiaEncontrarPacientePorId() throws ResourceNotFoundException, BadRequestException {
        PacienteDto pacienteEncontrado = pacienteService.buscarPacientePorId(1L);
        assertEquals(1, pacienteEncontrado.getId());
    }

    @Test
    @Order(4)
    void deberiaEliminarPacientePorId() throws ResourceNotFoundException, BadRequestException {
        pacienteService.eliminarPaciente(1L);
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscarPacientePorId(1L));
    }
}