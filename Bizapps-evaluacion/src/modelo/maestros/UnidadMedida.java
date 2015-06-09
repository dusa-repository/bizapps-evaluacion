package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the unidad_medida database table.
 * 
 */
@Entity
@Table(name="unidad_medida")
public class UnidadMedida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_unidad")
	private int id;

	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="usuario")
	private String usuario;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;
	

	public UnidadMedida() {
	}
	
	public UnidadMedida(int idUnidad, String descripcion, String usuario,
			Timestamp fechaAuditoria, String horaAuditoria) {
		super();
		this.id = idUnidad;
		this.descripcion = descripcion;
		this.usuario = usuario;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int idUnidad) {
		this.id = idUnidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	
}