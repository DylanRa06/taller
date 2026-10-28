package com.krakedev.taller.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "reparaciones")
public class Reparacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn (name = "vehiculo_placa", nullable = false)
	private Vehiculo vehiculo; 
	
	
	@ManyToMany
	@JoinTable (name = "reparacion_mecanicos",joinColumns =@JoinColumn (name = "reparacion_id"),
	inverseJoinColumns = @JoinColumn(name = "mecanico_id"))
	
	private  List<Mecanico> mecanicos;
	
	@Column(nullable = false)
	private double costo;
	
	@Column
	private String descripcion;
	
	
	@Column(nullable = false)
	private LocalDate fechaIngreso;
	
	
	@Column(nullable = false)
	private LocalDateTime fechaEntrega;
	
	


	@JsonCreator
	public Reparacion() {
		super();
	}


	public Reparacion( Vehiculo vehiculo, List<Mecanico> mecanicos, double costo, String descripcion,
			LocalDate fechaIngreso, LocalDateTime fechaEntrega) {
		super();
		
		this.vehiculo = vehiculo;
		this.mecanicos = mecanicos;
		this.costo = costo;
		this.descripcion = descripcion;
		this.fechaIngreso = fechaIngreso;
		this.fechaEntrega = fechaEntrega;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Vehiculo getVehiculo() {
		return vehiculo;
	}


	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}


	public List<Mecanico> getMecanicos() {
		return mecanicos;
	}


	public void setMecanicos(List<Mecanico> mecanicos) {
		this.mecanicos = mecanicos;
	}


	public double getCosto() {
		return costo;
	}


	public void setCosto(double costo) {
		this.costo = costo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}


	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}


	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}


	public void setFechaEntrega(LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	
	
	
	
}
