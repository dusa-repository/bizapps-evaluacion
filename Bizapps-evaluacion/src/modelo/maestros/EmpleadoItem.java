package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.pk.EmpleadoItemPK;

/**
 * The persistent class for the empleado_item database table.
 * 
 */
@Entity
@Table(name = "empleado_item")
@IdClass(EmpleadoItemPK.class)
public class EmpleadoItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
	private Empleado empleado;

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_item", referencedColumnName = "id_item")
	private ItemEvaluacion item;

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
	private Curso curso;
	
	
	@Column(name = "fecha_evaluacion")
	private Timestamp fecha;

	@Column(name = "valor_evaluacion")
	private String valorEvaluacion;

	public EmpleadoItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmpleadoItem(Empleado empleado, ItemEvaluacion item, Curso curso,
			Timestamp fecha, String valorEvaluacion) {
		super();
		this.empleado = empleado;
		this.item = item;
		this.curso = curso;
		this.fecha = fecha;
		this.valorEvaluacion = valorEvaluacion;
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

	public String getValorEvaluacion() {
		return valorEvaluacion;
	}

	public void setValorEvaluacion(String valorEvaluacion) {
		this.valorEvaluacion = valorEvaluacion;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	

}

