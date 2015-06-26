package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import modelo.pk.EmpleadoItemPK;

/**
 * The persistent class for the empleado_item database table.
 * 
 */
@Entity
@Table(name = "empleado_item")
public class EmpleadoItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpleadoItemPK id;	
	
	@Column(name = "fecha_evaluacion")
	private Timestamp fecha;

	@Column(name = "valor_evaluacion")
	private String valorEvaluacion;

	public EmpleadoItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmpleadoItem(EmpleadoItemPK id,
			Timestamp fecha, String valorEvaluacion) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.valorEvaluacion = valorEvaluacion;
	}

	public EmpleadoItemPK getId() {
		return id;
	}

	public void setId(EmpleadoItemPK id) {
		this.id = id;
	}

	public String getValorEvaluacion() {
		return valorEvaluacion;
	}

	public void setValorEvaluacion(String valorEvaluacion) {
		this.valorEvaluacion = valorEvaluacion;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	

}

