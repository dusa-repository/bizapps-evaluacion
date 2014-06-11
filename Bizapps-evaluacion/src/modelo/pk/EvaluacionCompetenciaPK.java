package modelo.pk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the evaluacion_competencia database table.
 * 
 */
@Embeddable
public class EvaluacionCompetenciaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_evaluacion")
	private int idEvaluacion;

	@Column(name="id_competencia")
	private int idCompetencia;

	public EvaluacionCompetenciaPK() {
	}
	public int getIdEvaluacion() {
		return this.idEvaluacion;
	}
	public void setIdEvaluacion(int idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}
	public int getIdCompetencia() {
		return this.idCompetencia;
	}
	public void setIdCompetencia(int idCompetencia) {
		this.idCompetencia = idCompetencia;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EvaluacionCompetenciaPK)) {
			return false;
		}
		EvaluacionCompetenciaPK castOther = (EvaluacionCompetenciaPK)other;
		return 
			(this.idEvaluacion == castOther.idEvaluacion)
			&& (this.idCompetencia == castOther.idCompetencia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEvaluacion;
		hash = hash * prime + this.idCompetencia;
		
		return hash;
	}
}