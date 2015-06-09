package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.pk.NivelCompetenciaCargoPK;


/**
 * The persistent class for the nivel_competencia_cargo database table.
 * 
 */
@Entity
@Table(name="nivel_competencia_cargo")
@IdClass(NivelCompetenciaCargoPK.class)
public class NivelCompetenciaCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_competencia", referencedColumnName = "id_competencia")
	private Competencia competencia;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
	private Cargo cargo;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dominio", referencedColumnName = "id_dominio")
	private Dominio dominio;
	

	public NivelCompetenciaCargo() {
		super ();
	}
	
	
	public NivelCompetenciaCargo(Competencia competencia, Cargo cargo,
			Dominio dominio) {
		super();
		this.competencia = competencia;
		this.cargo = cargo;
		this.dominio = dominio;
	}



	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	public Competencia getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	

}