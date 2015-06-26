package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;

/**
 * The primary key class for the empleado_curso database table.
 * 
 */
@Embeddable
public class EmpleadoCursoPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
	private Curso curso;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
	private Empleado empleado;

	public EmpleadoCursoPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	
	
	
	
}