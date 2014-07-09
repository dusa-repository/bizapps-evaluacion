package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

/**
 * The persistent class for the distribucion database table.
 * 
 */
@Entity
@Table(name = "distribucion")
public class Distribucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_distribucion")
	private int id;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "usuario")
	private String usuario;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria")
	private String horaAuditoria;

	@Column(name = "porcentaje")
	private int porcentaje;

	public Distribucion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Distribucion(int id, String descripcion, String usuario,
			Timestamp fechaAuditoria, String horaAuditoria, int porcentaje) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.usuario = usuario;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.porcentaje = porcentaje;
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

	public int getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	
	

	
}
