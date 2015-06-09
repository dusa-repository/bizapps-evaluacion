package modelo.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the evaluacion_objetivo database table.
 * 
 */
@Entity
@Table(name="evaluacion_capacitacion")
public class EvaluacionCapacitacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_capacitacion")
	private int idCapacitacion;

	@Column(name="descripcion_formacion")
	private String descripcionFormacion;

	@Column(name="id_evaluacion")
	private int idEvaluacion;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_area", referencedColumnName = "id_area")
	private Area area;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_formacion", referencedColumnName = "id_tipo_formacion")
	private TipoFormacion tipoFormacion;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_urgencia", referencedColumnName = "id_urgencia")
	private Urgencia urgencia;

	public EvaluacionCapacitacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdCapacitacion() {
		return idCapacitacion;
	}

	public void setIdCapacitacion(int idCapacitacion) {
		this.idCapacitacion = idCapacitacion;
	}

	public String getDescripcionFormacion() {
		return descripcionFormacion;
	}

	public void setDescripcionFormacion(String descripcionFormacion) {
		this.descripcionFormacion = descripcionFormacion;
	}

	public int getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(int idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public TipoFormacion getTipoFormacion() {
		return tipoFormacion;
	}

	public void setTipoFormacion(TipoFormacion tipoFormacion) {
		this.tipoFormacion = tipoFormacion;
	}

	public Urgencia getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(Urgencia urgencia) {
		this.urgencia = urgencia;
	}
	



}
