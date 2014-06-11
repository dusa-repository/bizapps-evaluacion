package modelos;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the menu database table.
 * 
 */
@Entity
@Table(name="menu")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_menu")
	private int idMenu;

	private String descripcion;

	private String enlace;

	private boolean estatus;

	@Column(name="fecha_auditoria")
	private Time fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	@Column(name="id_padre")
	private int idPadre;

	public Menu() {
	}

	public int getIdMenu() {
		return this.idMenu;
	}

	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEnlace() {
		return this.enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	public boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
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

	public int getIdPadre() {
		return this.idPadre;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

}