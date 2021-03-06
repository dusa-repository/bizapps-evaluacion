package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the urgencia database table.
 * 
 */
@Entity
@Table(name="urgencia")
public class Urgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_urgencia")
	private int id;

	@Column(name="descripcion_urgencia")
	private String descripcionUrgencia;
	
	@Column(name="usuario")
	private String usuario;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;
	

	public Urgencia() {
	}
	
	public Urgencia(int idUrgencia, String descripcionUrgencia, String usuario,
			Timestamp fechaAuditoria, String horaAuditoria) {
		super();
		this.id = idUrgencia;
		this.descripcionUrgencia = descripcionUrgencia;
		this.usuario = usuario;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int idUrgencia) {
		this.id = idUrgencia;
	}

	public String getDescripcionUrgencia() {
		return this.descripcionUrgencia;
	}

	public void setDescripcionUrgencia(String descripcionUrgencia) {
		this.descripcionUrgencia = descripcionUrgencia;
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