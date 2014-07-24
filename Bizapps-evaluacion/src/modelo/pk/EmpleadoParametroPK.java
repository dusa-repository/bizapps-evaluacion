package modelo.pk;

import java.io.Serializable;
import javax.persistence.*;

import modelo.maestros.Empleado;
import modelo.maestros.Parametro;

/**
 * The primary key class for the empleado_clase database table.
 * 
 */
@Embeddable
public class EmpleadoParametroPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Empleado empleado;

	private Parametro parametro;

	public EmpleadoParametroPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}


}