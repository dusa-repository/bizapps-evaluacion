package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;

import modelo.maestros.Curso;
import modelo.maestros.Empleado;
import modelo.maestros.ItemEvaluacion;

/**
 * The primary key class for the empleado_clase database table.
 * 
 */
@Embeddable
public class EmpleadoItemPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Empleado empleado;

	private ItemEvaluacion item;
	
	private Curso curso;

	public EmpleadoItemPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public ItemEvaluacion getItem() {
		return item;
	}

	public void setItem(ItemEvaluacion item) {
		this.item = item;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}