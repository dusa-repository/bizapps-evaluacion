package modelos;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the medicion database table.
 * 
 */
@Entity
@Table(name="medicion")
public class Medicion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_medicion")
	private int idMedicion;

	@Column(name="descripcion_medicion")
	private String descripcionMedicion;
	
	@Column(name="usuario")
	private String usuario;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;
	
	public Medicion() {
	}

	public int getIdMedicion() {
		return this.idMedicion;
	}

	public void setIdMedicion(int idMedicion) {
		this.idMedicion = idMedicion;
	}

	public String getDescripcionMedicion() {
		return this.descripcionMedicion;
	}

	public void setDescripcionMedicion(String descripcionMedicion) {
		this.descripcionMedicion = descripcionMedicion;
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