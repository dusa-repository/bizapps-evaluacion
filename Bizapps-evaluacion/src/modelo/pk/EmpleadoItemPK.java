package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
	private Empleado empleado;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item", referencedColumnName = "id_item")
	private ItemEvaluacion item;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
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