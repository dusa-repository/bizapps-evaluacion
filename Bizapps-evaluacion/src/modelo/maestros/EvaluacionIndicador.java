package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the evaluacion_indicador database table.
 * 
 */
@Entity
@Table(name="evaluacion_indicador")
public class EvaluacionIndicador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_indicador")
	private int idIndicador;

	@Column(name="descripcion_indicador")
	private String descripcionIndicador;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_medicion", referencedColumnName = "id_medicion")
	private Medicion medicion;

	@Column(name="id_objetivo")
	private int idObjetivo;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_unidad", referencedColumnName = "id_unidad")
	private UnidadMedida unidadMedida;
	

	private int linea;

	private double peso;

	@Column(name="resultado_fy_anterior")
	private double resultadoFyAnterior;

	@Column(name="resultado_peso")
	private double resultadoPeso;

	@Column(name="resultado_porc")
	private double resultadoPorc;

	private double total;

	@Column(name="valor_meta")
	private double valorMeta;

	@Column(name="valor_resultado")
	private double valorResultado;

	public EvaluacionIndicador() {
	}

	public int getIdIndicador() {
		return this.idIndicador;
	}

	public void setIdIndicador(int idIndicador) {
		this.idIndicador = idIndicador;
	}

	public String getDescripcionIndicador() {
		return this.descripcionIndicador;
	}

	public void setDescripcionIndicador(String descripcionIndicador) {
		this.descripcionIndicador = descripcionIndicador;
	}

	public Medicion getMedicion() {
		return medicion;
	}

	public void setMedicion(Medicion medicion) {
		this.medicion = medicion;
	}

	public int getIdObjetivo() {
		return this.idObjetivo;
	}

	public void setIdObjetivo(int idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
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

	public double getResultadoFyAnterior() {
		return this.resultadoFyAnterior;
	}

	public void setResultadoFyAnterior(double resultadoFyAnterior) {
		this.resultadoFyAnterior = resultadoFyAnterior;
	}

	public double getResultadoPeso() {
		return this.resultadoPeso;
	}

	public void setResultadoPeso(double resultadoPeso) {
		this.resultadoPeso = resultadoPeso;
	}

	public double getResultadoPorc() {
		return this.resultadoPorc;
	}

	public void setResultadoPorc(double resultadoPorc) {
		this.resultadoPorc = resultadoPorc;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getValorMeta() {
		return this.valorMeta;
	}

	public void setValorMeta(double valorMeta) {
		this.valorMeta = valorMeta;
	}

	public double getValorResultado() {
		return this.valorResultado;
	}

	public void setValorResultado(double valorResultado) {
		this.valorResultado = valorResultado;
	}

}