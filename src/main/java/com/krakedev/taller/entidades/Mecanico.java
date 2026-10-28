package com.krakedev.taller.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "mecanicos")


public class Mecanico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 50)
	private String nombre; 
	
	@Column(nullable = false, length = 50)
	private String especialidad; 
	
	@JsonIgnore
	@ManyToMany(mappedBy = "mecanicos")
	private List<Reparacion>reparaciones;
	
	

	@JsonCreator
	public Mecanico() {
		super();
	}

	public Mecanico(int id, String nombre, String especialidad, List<Reparacion> reparaciones) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.especialidad = especialidad;
		this.reparaciones = reparaciones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public List<Reparacion> getReparaciones() {
		return reparaciones;
	}

	public void setReparaciones(List<Reparacion> reparaciones) {
		this.reparaciones = reparaciones;
	}
	

}
