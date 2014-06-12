package modelos;

import java.io.Serializable;
import javax.persistence.*;
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
	
	
	@Column(name="id_dominio")
	private int idDominio;

	public NivelCompetenciaCargo() {
		super ();
	}

	public int getIdDominio() {
		return this.idDominio;
	}

	public void setIdDominio(int idDominio) {
		this.idDominio = idDominio;
	}

}