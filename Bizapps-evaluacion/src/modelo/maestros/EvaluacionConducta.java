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

import modelo.pk.EvaluacionConductaPK;


/**
 * The persistent class for the evaluacion_conducta database table.
 * 
 */
@Entity
@Table(name="evaluacion_conducta")
public class EvaluacionConducta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private  EvaluacionConductaPK id;	
	
	@ManyToOne
	@JoinColumn(name = "id_competencia")
	private Competencia competencia;
	
	private String observacion;

	private boolean valor;

	public EvaluacionConducta() {
	}

	public EvaluacionConductaPK getId() {
		return id;
	}


	public void setId(EvaluacionConductaPK id) {
		this.id = id;
	}


	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public boolean getValor() {
		return this.valor;
	}

	public void setValor(boolean valor) {
		this.valor = valor;
	}


	public Competencia getCompetencia() {
		return competencia;
	}


	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}
	

}