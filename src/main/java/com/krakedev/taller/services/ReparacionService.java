package com.krakedev.taller.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.krakedev.taller.entidades.Mecanico;
import com.krakedev.taller.entidades.Reparacion;
import com.krakedev.taller.entidades.Vehiculo;
import com.krakedev.taller.repositories.MecanicoRepository;
import com.krakedev.taller.repositories.ReparacionRepository;
import com.krakedev.taller.repositories.VehiculoRepository;

@Service
public class ReparacionService {
    private final ReparacionRepository repository;
    private final VehiculoRepository vehiculoRepository;
    private final MecanicoRepository mecanicoRepository;

    public ReparacionService(ReparacionRepository repository,
            VehiculoRepository vehiculoRepository, MecanicoRepository mecanicoRepository) {
        this.repository = repository;
        this.vehiculoRepository = vehiculoRepository;
        this.mecanicoRepository = mecanicoRepository;
    }

    public Reparacion guardar(Reparacion reparacion) {
        if (reparacion.getVehiculo() == null || reparacion.getVehiculo().getPlaca() == null) {
            throw new IllegalArgumentException("Debe indicar el vehículo");
        }
        Vehiculo vehiculo = vehiculoRepository.findById(reparacion.getVehiculo().getPlaca())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no existe"));
        if (reparacion.getMecanicos() == null) {
            throw new IllegalArgumentException("Debe indicar la lista de mecánicos");
        }
        List<Mecanico> mecanicosDB = new ArrayList<>();
        for (Mecanico mecanico : reparacion.getMecanicos()) {
            if (mecanico == null) {
                throw new IllegalArgumentException("Mecánico no existe");
            }
            Mecanico mecanicoReal = mecanicoRepository.findById(mecanico.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Mecánico no existe"));
            mecanicosDB.add(mecanicoReal);
        }
        reparacion.setVehiculo(vehiculo);
        reparacion.setMecanicos(mecanicosDB);
        return repository.save(reparacion);
    }

    public List<Reparacion> listar() {
        return repository.findAll();
    }

    public Reparacion buscar(int id) {
        return repository.findById(id).orElse(null);
    }

    public Reparacion actualizar(int id, Reparacion datos) {
        Reparacion actual = buscar(id);
        if (actual == null) {
            return null;
        }
        actual.setCosto(datos.getCosto());
        actual.setDescripcion(datos.getDescripcion());
        actual.setFechaIngreso(datos.getFechaIngreso());
        actual.setFechaEntrega(datos.getFechaEntrega());
        return repository.save(actual);
    }

    public boolean eliminar(int id) {
        Reparacion actual = buscar(id);
        if (actual == null) {
            return false;
        }
        repository.delete(actual);
        return true;
    }
}
