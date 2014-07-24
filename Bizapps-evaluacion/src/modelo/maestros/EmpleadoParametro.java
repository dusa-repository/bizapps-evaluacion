package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.pk.EmpleadoParametroPK;

/**
 * The persistent class for the empleado_parametro database table.
 * 
 */
@Entity
@Table(name = "empleado_parametro")
@IdClass(EmpleadoParametroPK.class)
public class EmpleadoParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
	private Empleado empleado;

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_parametro", referencedColumnName = "id_parametro")
	private Parametro parametro;

	// bi-directional many-to-one association to TipoFormacion
	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;

	@Column(name = "valor_evaluacion")
	private String valorEvaluacion;

	public EmpleadoParametro() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EmpleadoParametro(Empleado empleado, Parametro parametro,
			Curso curso, String valorEvaluacion) {
		super();
		this.empleado = empleado;
		this.parametro = parametro;
		this.curso = curso;
		this.valorEvaluacion = valorEvaluacion;
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

}
