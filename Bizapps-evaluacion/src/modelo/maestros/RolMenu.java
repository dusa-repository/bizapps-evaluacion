package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.pk.RolMenuPK;

import java.sql.Time;


/**
 * The persistent class for the rol_menu database table.
 * 
 */
@Entity
@Table(name="rol_menu")
public class RolMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolMenuPK id;

	@Column(name="fecha_auditoria")
	private Time fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	public RolMenu() {
	}

	public RolMenuPK getId() {
		return this.id;
	}

	public void setId(RolMenuPK id) {
		this.id = id;
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