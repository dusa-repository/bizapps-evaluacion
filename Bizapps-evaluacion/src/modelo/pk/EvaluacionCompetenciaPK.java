package modelo.pk;

import java.io.Serializable;
import javax.persistence.*;

import modelo.maestros.Competencia;
import modelo.maestros.Evaluacion;

/**
 * The primary key class for the evaluacion_competencia database table.
 * 
 */
@Embeddable
public class EvaluacionCompetenciaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

//	@Column(name="id_evaluacion")
	private Evaluacion evaluacion;

//	@Column(name="id_competencia")
	private  Competencia competencia;

	public EvaluacionCompetenciaPK() {
	}

	public Evaluacion getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Competencia getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}
	
	
//	public int getIdEvaluacion() {
//		return this.idEvaluacion;
//	}
//	public void setIdEvaluacion(int idEvaluacion) {
//		this.idEvaluacion = idEvaluacion;
//	}
//	public int getIdCompetencia() {
//		return this.idCompetencia;
//	}
//	public void setIdCompetencia(int idCompetencia) {
//		this.idCompetencia = idCompetencia;
//	}
//
//	public boolean equals(Object other) {
//		if (this == other) {
//			return true;
//		}
//		if (!(other instanceof EvaluacionCompetenciaPK)) {
//			return false;
//		}
//		EvaluacionCompetenciaPK castOther = (EvaluacionCompetenciaPK)other;
//		return 
//			(this.idEvaluacion == castOther.idEvaluacion)
//			&& (this.idCompetencia == castOther.idCompetencia);
//	}
//
//	public int hashCode() {
//		final int prime = 31;
//		int hash = 17;
//		hash = hash * prime + this.idEvaluacion;
//		hash = hash * prime + this.idCompetencia;
//		
//		return hash;
//	}
}