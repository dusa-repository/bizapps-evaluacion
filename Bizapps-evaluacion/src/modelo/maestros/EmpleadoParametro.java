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

import modelo.pk.EmpleadoParametroPK;

/**
 * The persistent class for the empleado_parametro database table.
 * 
 */
@Entity
@Table(name = "empleado_parametro")
public class EmpleadoParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpleadoParametroPK id;

	@Column(name = "valor_evaluacion")
	private String valorEvaluacion;

	public EmpleadoParametro() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EmpleadoParametro(EmpleadoParametroPK id, String valorEvaluacion) {
		super();
		this.id = id;
		this.valorEvaluacion = valorEvaluacion;
	}

	public EmpleadoParametroPK getId() {
		return id;
	}

	public void setId(EmpleadoParametroPK id) {
		this.id = id;
	}

	public String getValorEvaluacion() {
		return valorEvaluacion;
	}

	public void setValorEvaluacion(String valorEvaluacion) {
		this.valorEvaluacion = valorEvaluacion;
	}

}
