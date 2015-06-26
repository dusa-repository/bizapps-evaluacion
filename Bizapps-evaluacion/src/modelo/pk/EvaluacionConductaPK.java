package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.ConductaCompetencia;
import modelo.maestros.Evaluacion;

/**
 * The primary key class for the evaluacion_conducta database table.
 * 
 */
@Embeddable
public class EvaluacionConductaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_evaluacion", referencedColumnName = "id_evaluacion")
	private Evaluacion evaluacion;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_conducta", referencedColumnName = "id_conducta")
	private ConductaCompetencia conductaCompetencia;


	public EvaluacionConductaPK() {
	}


	public Evaluacion getEvaluacion() {
		return evaluacion;
	}


	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}


	public ConductaCompetencia getConductaCompetencia() {
		return conductaCompetencia;
	}


	public void setConductaCompetencia(ConductaCompetencia conductaCompetencia) {
		this.conductaCompetencia = conductaCompetencia;
	}
	
	
//	public int getIdEvaluacion() {
//		return this.idEvaluacion;
//	}
//	public void setIdEvaluacion(int idEvaluacion) {
//		this.idEvaluacion = idEvaluacion;
//	}
//	public int getIdConducta() {
//		return this.idConducta;
//	}
//	public void setIdConducta(int idConducta) {
//		this.idConducta = idConducta;
//	}
//
//	public boolean equals(Object other) {
//		if (this == other) {
//			return true;
//		}
//		if (!(other instanceof EvaluacionConductaPK)) {
//			return false;
//		}
//		EvaluacionConductaPK castOther = (EvaluacionConductaPK)other;
//		return 
//			(this.idEvaluacion == castOther.idEvaluacion)
//			&& (this.idConducta == castOther.idConducta);
//	}
//
//	public int hashCode() {
//		final int prime = 31;
//		int hash = 17;
//		hash = hash * prime + this.idEvaluacion;
//		hash = hash * prime + this.idConducta;
//		
//		return hash;
//	}
}