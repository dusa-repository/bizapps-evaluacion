package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the area database table.
 * 
 */
@Entity
@Table(name="area")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_area")
	private int idArea;

	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="usuario")
	private String usuario;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	//bi-directional many-to-one association to TipoFormacion
	@OneToMany(mappedBy="area")
	private List<TipoFormacion> tipoFormacions;

	public Area() {
	}
	
	public Area(int idArea, String descripcion, String usuario,
			Timestamp fechaAuditoria, String horaAuditoria) {
		super();
		this.idArea = idArea;
		this.descripcion = descripcion;
		this.usuario = usuario;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
	}



	public int getIdArea() {
		return this.idArea;
	}

	public void setIdArea(int idArea) {
		this.idArea = idArea;
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

	public List<TipoFormacion> getTipoFormacions() {
		return this.tipoFormacions;
	}

	public void setTipoFormacions(List<TipoFormacion> tipoFormacions) {
		this.tipoFormacions = tipoFormacions;
	}

	public TipoFormacion addTipoFormacion(TipoFormacion tipoFormacion) {
		getTipoFormacions().add(tipoFormacion);
		tipoFormacion.setArea(this);

		return tipoFormacion;
	}

	public TipoFormacion removeTipoFormacion(TipoFormacion tipoFormacion) {
		getTipoFormacions().remove(tipoFormacion);
		tipoFormacion.setArea(null);

		return tipoFormacion;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	

}