package modelos;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@Table(name="rol")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_rol")
	private int idRol;

	private String descripcion;

	@Column(name="fecha_auditoria")
	private Time fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	public Rol() {
	}

	public int getIdRol() {
		return this.idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Time getFechaAuditoria() {
		return this.fechaAuditoria;
	}

	public void setFechaAuditoria(Time fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return this.horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

}