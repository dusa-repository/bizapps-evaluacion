package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.pk.EmpleadoClasePK;

/**
 * The persistent class for the nivel_competencia_cargo database table.
 * 
 */
@Entity
@Table(name = "empleado_clase")
@IdClass(EmpleadoClasePK.class)
public class EmpleadoClase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
	private Clase clase;

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
	private Empleado empleado;

	@Column(name = "asistencia")
	private String asistencia;

	public EmpleadoClase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public String getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}
	
}