package com.krakedev.taller.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.krakedev.taller.entidades.Reparacion;
import com.krakedev.taller.services.ReparacionService;

@RestController
@RequestMapping("/api/reparaciones")
public class ReparacionController {
    private final ReparacionService service;

    public ReparacionController(ReparacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Reparacion datos) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(datos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Reparacion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reparacion> buscar(@PathVariable("id") int id) {
        Reparacion encontrado = service.buscar(id);
        if (encontrado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(encontrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") int id,
            @RequestBody Reparacion datos) {
        try {
            Reparacion actualizado = service.actualizar(id, datos);
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
