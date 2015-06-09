package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the tipo_formacion database table.
 * 
 */
@Entity
@Table(name = "tipo_formacion")
public class TipoFormacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_tipo_formacion")
	private int id;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria")
	private String horaAuditoria;

	// bi-directional many-to-one association to area
	@OneToMany(mappedBy = "tipoFormacion")
	private List<Area> areas;

	@Column(name = "usuario")
	private String usuario;

	public TipoFormacion() {
	}

	public TipoFormacion(int id, String descripcion, Timestamp fechaAuditoria,
			String horaAuditoria, String usuario) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuario = usuario;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int idTipoFormacion) {
		this.id = idTipoFormacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


}