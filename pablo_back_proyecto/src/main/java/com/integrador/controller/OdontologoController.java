package com.integrador.controller;

import com.integrador.dto.OdontologoDto;
import com.integrador.entity.Odontologo;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import com.integrador.service.IOdontologoService;
import com.integrador.service.impl.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/odontologos")
public class OdontologoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final IOdontologoService odontologoService;

    @Autowired
    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @Operation(summary = "Listado de odontologos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurso correcto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public List<OdontologoDto> listarOdontologos() throws ResourceNotFoundException {
        return odontologoService.listarOdontologos();
    }

    @Operation(summary = "Buscar id odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontologo correctamente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDto> buscarOdontologoPorId(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscarOdontologoPorId(id));
    }

    @Operation(summary = "Registro de odontologos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontologo correctamente registrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OdontologoDto> registrarOdontologo(@Valid @RequestBody Odontologo odontologo) throws BadRequestException {
        LOGGER.info("que pasaaa");
        return new ResponseEntity<>(odontologoService.registrarOdontologo(odontologo), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontologo correctamente actualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PutMapping("/actualizar")
    public ResponseEntity<OdontologoDto> actualizarOdontologo(@Valid @RequestBody Odontologo odontologo) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.actualizarOdontologo(odontologo));
    }

    @Operation(summary = "Eliminar odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontologo correctamente eliminado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("Odontologo eliminado");
    }
}
