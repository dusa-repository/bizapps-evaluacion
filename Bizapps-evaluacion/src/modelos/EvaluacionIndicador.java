package modelos;

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
	@Column(name="id_indicador")
	private int idIndicador;

	@Column(name="descripcion_indicador")
	private String descripcionIndicador;

	@Column(name="id_medicion")
	private int idMedicion;

	@Column(name="id_objetivo")
	private int idObjetivo;

	@Column(name="id_unidad")
	private int idUnidad;

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

	public int getIdMedicion() {
		return this.idMedicion;
	}

	public void setIdMedicion(int idMedicion) {
		this.idMedicion = idMedicion;
	}

	public int getIdObjetivo() {
		return this.idObjetivo;
	}

	public void setIdObjetivo(int idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public int getIdUnidad() {
		return this.idUnidad;
	}

	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
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