package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.Clase;
import modelo.maestros.Empleado;

/**
 * The primary key class for the empleado_clase database table.
 * 
 */
@Embeddable
public class EmpleadoClasePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
	private Clase clase;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
	private Empleado empleado;

	public EmpleadoClasePK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

}