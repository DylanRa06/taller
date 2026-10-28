package com.krakedev.taller.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.krakedev.taller.entidades.Mecanico;
import com.krakedev.taller.repositories.MecanicoRepository;

@Service
public class MecanicoService {
    private final MecanicoRepository repository;

    public MecanicoService(MecanicoRepository repository) {
        this.repository = repository;
    }

    public Mecanico guardar(Mecanico datos) {
        return repository.save(datos);
    }

    public List<Mecanico> listar() {
        return repository.findAll();
    }

    public Mecanico buscar(int id) {
        return repository.findById(id).orElse(null);
    }

    public Mecanico actualizar(int id, Mecanico datos) {
        Mecanico actual = buscar(id);
        if (actual == null) {
            return null;
        }
        actual.setNombre(datos.getNombre());
        actual.setEspecialidad(datos.getEspecialidad());
        return repository.save(actual);
    }

    public boolean eliminar(int id) {
        Mecanico actual = buscar(id);
        if (actual == null) {
            return false;
        }
        repository.delete(actual);
        return true;
    }
}
