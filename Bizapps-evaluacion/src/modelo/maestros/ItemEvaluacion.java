package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the item_evaluacion database table.
 * 
 */
@Entity
@Table(name = "item_evaluacion")
public class ItemEvaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_item")
	private int id;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "ponderacion")
	private String ponderacion;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria")
	private String horaAuditoria;

	@Column(name = "usuario")
	private String usuario;

	public ItemEvaluacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemEvaluacion(int id, String descripcion, String ponderacion,
			Timestamp fechaAuditoria, String horaAuditoria, String usuario) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.ponderacion = ponderacion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}

	public Timestamp getFechaAuditoria() {
		return fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
