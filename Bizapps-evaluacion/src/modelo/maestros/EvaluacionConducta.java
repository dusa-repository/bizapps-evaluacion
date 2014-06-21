package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.pk.EvaluacionConductaPK;


/**
 * The persistent class for the evaluacion_conducta database table.
 * 
 */
@Entity
@Table(name="evaluacion_conducta")
public class EvaluacionConducta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EvaluacionConductaPK id;

	private String observacion;

	private boolean valor;

	public EvaluacionConducta() {
	}

	public EvaluacionConductaPK getId() {
		return this.id;
	}

	public void setId(EvaluacionConductaPK id) {
		this.id = id;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public boolean getValor() {
		return this.valor;
	}

	public void setValor(boolean valor) {
		this.valor = valor;
	}

}