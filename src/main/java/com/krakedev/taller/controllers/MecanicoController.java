package com.krakedev.taller.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.krakedev.taller.entidades.Mecanico;
import com.krakedev.taller.services.MecanicoService;

@RestController
@RequestMapping("/api/mecanicos")
public class MecanicoController {
    private final MecanicoService service;

    public MecanicoController(MecanicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Mecanico datos) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(datos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Mecanico> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mecanico> buscar(@PathVariable("id") int id) {
        Mecanico encontrado = service.buscar(id);
        if (encontrado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(encontrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") int id,
            @RequestBody Mecanico datos) {
        try {
            Mecanico actualizado = service.actualizar(id, datos);
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") int id) {
        try {
            if (!service.eliminar(id)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
