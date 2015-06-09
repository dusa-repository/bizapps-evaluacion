package modelo.maestros;

import java.io.Serializable;

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
@IdClass(EvaluacionConductaPK.class)
public class EvaluacionConducta implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_evaluacion", referencedColumnName = "id_evaluacion")
	private Evaluacion evaluacion;
	
	@Id
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_conducta", referencedColumnName = "id_conducta")
	private ConductaCompetencia conductaCompetencia;
	
	
	@ManyToOne
	@JoinColumn(name = "id_competencia")
	private Competencia competencia;
	
	private String observacion;

	private boolean valor;

	public EvaluacionConducta() {
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