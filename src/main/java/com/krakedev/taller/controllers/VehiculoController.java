package com.krakedev.taller.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.krakedev.taller.entidades.Vehiculo;
import com.krakedev.taller.services.VehiculoService;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final VehiculoService service;

    public VehiculoController(VehiculoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Vehiculo datos) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(datos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Vehiculo> listar() {
        return service.listar();
    }

    @GetMapping("/{placa}")
    public ResponseEntity<Vehiculo> buscar(@PathVariable("placa") String placa) {
        Vehiculo encontrado = service.buscar(placa);
        if (encontrado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(encontrado);
    }

    @PutMapping("/{placa}")
    public ResponseEntity<?> actualizar(@PathVariable("placa") String placa,
            @RequestBody Vehiculo datos) {
        try {
            Vehiculo actualizado = service.actualizar(placa, datos);
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<?> eliminar(@PathVariable("placa") String placa) {
        try {
            if (!service.eliminar(placa)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
