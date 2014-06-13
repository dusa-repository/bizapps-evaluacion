package modelos;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the evaluacion_objetivo database table.
 * 
 */
@Entity
@Table(name="evaluacion_objetivo")
public class EvaluacionObjetivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_objetivo")
	private int idObjetivo;

	private String corresponsables;

	@Column(name="descripcion_objetivo")
	private String descripcionObjetivo;

	@Column(name="id_evaluacion")
	private int idEvaluacion;

	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_perspectiva", referencedColumnName = "id_perspectiva")
	private Perspectiva perspectiva;
	

	private int linea;

	private double peso;

	private double resultado;

	@Column(name="total_ind")
	private double totalInd;

	public EvaluacionObjetivo() {
	}

	public int getIdObjetivo() {
		return this.idObjetivo;
	}

	public void setIdObjetivo(int idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public String getCorresponsables() {
		return this.corresponsables;
	}

	public void setCorresponsables(String corresponsables) {
		this.corresponsables = corresponsables;
	}

	public String getDescripcionObjetivo() {
		return this.descripcionObjetivo;
	}

	public void setDescripcionObjetivo(String descripcionObjetivo) {
		this.descripcionObjetivo = descripcionObjetivo;
	}

	public int getIdEvaluacion() {
		return this.idEvaluacion;
	}

	public void setIdEvaluacion(int idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}
	

	public Perspectiva getPerspectiva() {
		return perspectiva;
	}

	public void setPerspectiva(Perspectiva perspectiva) {
		this.perspectiva = perspectiva;
	}


	public int getLinea() {
		return this.linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public double getPeso() {
		return this.peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getResultado() {
		return this.resultado;
	}

	public void setResultado(double resultado) {
		this.resultado = resultado;
	}

	public double getTotalInd() {
		return this.totalInd;
	}

	public void setTotalInd(double totalInd) {
		this.totalInd = totalInd;
	}

}