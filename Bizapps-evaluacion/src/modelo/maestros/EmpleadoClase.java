package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.pk.EmpleadoClasePK;

/**
 * The persistent class for the nivel_competencia_cargo database table.
 * 
 */
@Entity
@Table(name = "empleado_clase")
public class EmpleadoClase implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpleadoClasePK id;

	@Column(name = "asistencia")
	private String asistencia;

	public EmpleadoClase() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public EmpleadoClase(EmpleadoClasePK id, String asistencia) {
		super();
		this.id = id;
		this.asistencia = asistencia;
	}

	public EmpleadoClasePK getId() {
		return id;
	}


	public void setId(EmpleadoClasePK id) {
		this.id = id;
	}


	public String getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}
	
}