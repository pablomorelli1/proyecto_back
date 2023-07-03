package com.integrador.controller;

import com.integrador.dto.PacienteDto;
import com.integrador.entity.Paciente;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import com.integrador.service.IPacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin
public class PacienteController {
    private IPacienteService pacienteService;

    @Autowired
    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(summary = "Registrar paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente correctamente registrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registrar")
    public ResponseEntity<PacienteDto> registrarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        PacienteDto pacienteDto = pacienteService.guardarPaciente(paciente);
        if (pacienteDto != null) {
            return new ResponseEntity<>(pacienteDto, null, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Update paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente correctamente actualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/actualizar")
    public ResponseEntity<PacienteDto> actualizarPaciente(@RequestBody Paciente paciente) throws BadRequestException, ResourceNotFoundException {
        PacienteDto pacienteDto = pacienteService.actualizarPaciente(paciente);
        if (pacienteDto != null) {
            return new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Listar pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recuerso correcto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<PacienteDto> listarTodos() throws ResourceNotFoundException {
        return pacienteService.listarPacientes();
    }

    @Operation(summary = "Buscar id paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de pacientes",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> buscarPacientePorId(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        PacienteDto pacienteDto = pacienteService.buscarPacientePorId(id);
        if (pacienteDto != null) {
            return new ResponseEntity<>(pacienteDto, null, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Buscar paciente por apellido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente correctamente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/apellido/{apellido}")
    public List<PacienteDto> buscarPacientesPorApellido(@PathVariable String apellido) throws BadRequestException, ResourceNotFoundException {
        return pacienteService.buscarPacientesPorApellido(apellido);
    }

    @Operation(summary = "Eliminar paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente eliminado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("Paciente eliminado");
    }
}
