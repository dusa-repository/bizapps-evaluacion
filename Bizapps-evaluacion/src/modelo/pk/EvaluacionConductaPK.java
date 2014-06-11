package modelo.pk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the evaluacion_conducta database table.
 * 
 */
@Embeddable
public class EvaluacionConductaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_evaluacion")
	private int idEvaluacion;

	@Column(name="id_conducta")
	private int idConducta;

	public EvaluacionConductaPK() {
	}
	public int getIdEvaluacion() {
		return this.idEvaluacion;
	}
	public void setIdEvaluacion(int idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}
	public int getIdConducta() {
		return this.idConducta;
	}
	public void setIdConducta(int idConducta) {
		this.idConducta = idConducta;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EvaluacionConductaPK)) {
			return false;
		}
		EvaluacionConductaPK castOther = (EvaluacionConductaPK)other;
		return 
			(this.idEvaluacion == castOther.idEvaluacion)
			&& (this.idConducta == castOther.idConducta);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idEvaluacion;
		hash = hash * prime + this.idConducta;
		
		return hash;
	}
}