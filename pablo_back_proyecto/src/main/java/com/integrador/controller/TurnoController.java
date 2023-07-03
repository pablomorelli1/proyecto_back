package com.integrador.controller;

import com.integrador.dto.TurnoDto;
import com.integrador.entity.Turno;
import com.integrador.exception.BadRequestException;
import com.integrador.exception.ResourceNotFoundException;
import com.integrador.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
@CrossOrigin
public class TurnoController {
    private final ITurnoService turnoService;

    @Autowired
    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<TurnoDto> registrarTurno(@RequestBody Turno turno) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.guardarTurno(turno));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<TurnoDto> actualizarTurno(@RequestBody Turno turno) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.actualizarTurno(turno));
    }

    @GetMapping
    public List<TurnoDto> listarTodos() throws ResourceNotFoundException {
        return turnoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> buscarTurnoPorId(@PathVariable Long id) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarTurnoPorId(id));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException, BadRequestException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Turno eliminado");
    }
}
