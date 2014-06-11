package modelos;

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
	@Column(name="id_tipo_formacion")
	private int idTipoFormacion;

	private String descripcion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	//bi-directional many-to-one association to Area
	@ManyToOne
	@JoinColumn(name="id_area")
	private Area area;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

	public TipoFormacion() {
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

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}