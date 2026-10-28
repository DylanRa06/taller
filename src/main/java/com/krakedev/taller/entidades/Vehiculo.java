package com.krakedev.taller.entidades;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

	
	@Id
	private String placa;
	
	@Column(nullable = false)
	
	private String marca;
	
	@Column(nullable = false)
	
	private String modelo;
	
	@Column(nullable = false)
	
	private String anio;
	
	@JsonIgnore
	
	@OneToMany(mappedBy= "vehiculo")
	private List <Reparacion>reparaciones;

	
	
	
	@JsonCreator
	public Vehiculo() {
		super();
	}
	
	

	public Vehiculo(String placa, String marca, String modelo, String anio, List<Reparacion> reparaciones) {
		super();
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.anio = anio;
		this.reparaciones = reparaciones;
	}



	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public List<Reparacion> getReparaciones() {
		return reparaciones;
	}

	public void setReparaciones(List<Reparacion> reparaciones) {
		this.reparaciones = reparaciones;
	}
	
	
	
	
	
	
}
