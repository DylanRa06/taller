package com.krakedev.taller.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.krakedev.taller.entidades.Vehiculo;
import com.krakedev.taller.repositories.VehiculoRepository;

@Service
public class VehiculoService {
    private final VehiculoRepository repository;

    public VehiculoService(VehiculoRepository repository) {
        this.repository = repository;
    }

    public Vehiculo guardar(Vehiculo datos) {
        return repository.save(datos);
    }

    public List<Vehiculo> listar() {
        return repository.findAll();
    }

    public Vehiculo buscar(String placa) {
        return repository.findById(placa).orElse(null);
    }

    public Vehiculo actualizar(String placa, Vehiculo datos) {
        Vehiculo actual = buscar(placa);
        if (actual == null) {
            return null;
        }
        actual.setMarca(datos.getMarca());
        actual.setModelo(datos.getModelo());
        actual.setAnio(datos.getAnio());
        return repository.save(actual);
    }

    public boolean eliminar(String placa) {
        Vehiculo actual = buscar(placa);
        if (actual == null) {
            return false;
        }
        repository.delete(actual);
        return true;
    }
}
