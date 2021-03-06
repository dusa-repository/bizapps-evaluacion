package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.pk.EvaluacionCompetenciaPK;


/**
 * The persistent class for the evaluacion_competencia database table.
 * 
 */
@Entity
@Table(name="evaluacion_competencia")
public class EvaluacionCompetencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EvaluacionCompetenciaPK id;

	@Column(name="id_dominio")
	private int idDominio;

	public EvaluacionCompetencia() {
	}
	
	public EvaluacionCompetenciaPK getId() {
		return id;
	}

	public void setId(EvaluacionCompetenciaPK id) {
		this.id = id;
	}

	public int getIdDominio() {
		return this.idDominio;
	}

	public void setIdDominio(int idDominio) {
		this.idDominio = idDominio;
	}
	
}