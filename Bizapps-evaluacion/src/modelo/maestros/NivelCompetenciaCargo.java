package modelo.maestros;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
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
public class NivelCompetenciaCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NivelCompetenciaCargoPK id;
	
	public NivelCompetenciaCargo() {
		super ();
	}
	
	public NivelCompetenciaCargo(NivelCompetenciaCargoPK id) {
		super();
		this.id = id;
	}

	public NivelCompetenciaCargoPK getId() {
		return id;
	}
	
	public void setId(NivelCompetenciaCargoPK id) {
		this.id = id;
	}

}