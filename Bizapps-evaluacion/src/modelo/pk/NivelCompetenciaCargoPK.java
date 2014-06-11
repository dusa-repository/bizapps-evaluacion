package modelo.pk;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the nivel_competencia_cargo database table.
 * 
 */
@Embeddable
public class NivelCompetenciaCargoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_cargo")
	private String idCargo;

	@Column(name="id_competencia")
	private int idCompetencia;

	public NivelCompetenciaCargoPK() {
	}
	public String getIdCargo() {
		return this.idCargo;
	}
	public void setIdCargo(String idCargo) {
		this.idCargo = idCargo;
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
		if (!(other instanceof NivelCompetenciaCargoPK)) {
			return false;
		}
		NivelCompetenciaCargoPK castOther = (NivelCompetenciaCargoPK)other;
		return 
			this.idCargo.equals(castOther.idCargo)
			&& (this.idCompetencia == castOther.idCompetencia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idCargo.hashCode();
		hash = hash * prime + this.idCompetencia;
		
		return hash;
	}
}