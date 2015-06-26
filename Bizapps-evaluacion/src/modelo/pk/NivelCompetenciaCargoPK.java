package modelo.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import modelo.maestros.Cargo;
import modelo.maestros.Competencia;
import modelo.maestros.Dominio;

/**
 * The primary key class for the nivel_competencia_cargo database table.
 * 
 */
@Embeddable
public class NivelCompetenciaCargoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_competencia", referencedColumnName = "id_competencia")
	private Competencia competencia;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
	private Cargo cargo;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dominio", referencedColumnName = "id_dominio")
	private Dominio dominio;

	public NivelCompetenciaCargoPK() {
	}
	
	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	public Competencia getCompetencia() {
		return competencia;
	}
	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}
//	public boolean equals(Object other) {
//		if (this == other) {
//			return true;
//		}
//		if (!(other instanceof NivelCompetenciaCargoPK)) {
//			return false;
//		}
//		NivelCompetenciaCargoPK castOther = (NivelCompetenciaCargoPK)other;
//		return 
//			this.idCargo.equals(castOther.idCargo)
//			&& (this.idCompetencia == castOther.idCompetencia);
//	}
//
//	public int hashCode() {
//		final int prime = 31;
//		int hash = 17;
//		hash = hash * prime + this.idCargo.hashCode();
//		hash = hash * prime + this.idCompetencia;
//		
//		return hash;
//	}
}