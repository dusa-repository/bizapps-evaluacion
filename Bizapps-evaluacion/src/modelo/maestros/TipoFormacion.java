package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;


/**
 * The persistent class for the tipo_formacion database table.
 * 
 */
@Entity
@Table(name="tipo_formacion")
public class TipoFormacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_tipo_formacion")
	private int idTipoFormacion;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	//bi-directional many-to-one association to Area
	@ManyToOne
	@JoinColumn(name="id_area")
	private Area area;

	@Column(name="usuario")
	private String usuario;

	public TipoFormacion() {
	}
	
	public TipoFormacion(int idTipoFormacion, String descripcion,
			Timestamp fechaAuditoria, String horaAuditoria, Area area,
			String usuario) {
		super();
		this.idTipoFormacion = idTipoFormacion;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.area = area;
		this.usuario = usuario;
	}



	public int getIdTipoFormacion() {
		return this.idTipoFormacion;
	}

	public void setIdTipoFormacion(int idTipoFormacion) {
		this.idTipoFormacion = idTipoFormacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaAuditoria() {
		return this.fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public String getHoraAuditoria() {
		return this.horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	
}