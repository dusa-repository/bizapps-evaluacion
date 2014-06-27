package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.pk.EvaluacionConductaPK;
import modelo.pk.NivelCompetenciaCargoPK;


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

}