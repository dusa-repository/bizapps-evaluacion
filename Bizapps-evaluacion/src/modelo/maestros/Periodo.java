package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the periodo database table.
 * 
 */
@Entity
@Table(name="periodo")
public class Periodo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_periodo")
	private int idPeriodo;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="estado_periodo")
	private String estadoPeriodo;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	@Column(name="nombre")
	private String nombre;
	
	@Column(name="usuario")
	private String usuario;

	//bi-directional many-to-one association to Revision
	@OneToMany(mappedBy="periodo")
	private List<Revision> revisions;

	public Periodo() {
	}
	
	public Periodo(int idPeriodo, String descripcion, String estadoPeriodo,
			Timestamp fechaAuditoria, Timestamp fechaFin,
			Timestamp fechaInicio, String horaAuditoria, String nombre,
			String usuario) {
		super();
		this.idPeriodo = idPeriodo;
		this.descripcion = descripcion;
		this.estadoPeriodo = estadoPeriodo;
		this.fechaAuditoria = fechaAuditoria;
		this.fechaFin = fechaFin;
		this.fechaInicio = fechaInicio;
		this.horaAuditoria = horaAuditoria;
		this.nombre = nombre;
		this.usuario = usuario;
	}



	public int getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoPeriodo() {
		return this.estadoPeriodo;
	}

	public void setEstadoPeriodo(String estadoPeriodo) {
		this.estadoPeriodo = estadoPeriodo;
	}

	public Timestamp getFechaAuditoria() {
		return this.fechaAuditoria;
	}

	public void setFechaAuditoria(Timestamp fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}

	public Timestamp getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getHoraAuditoria() {
		return this.horaAuditoria;
	}

	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Revision> getRevisions() {
		return this.revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}

	public Revision addRevision(Revision revision) {
		getRevisions().add(revision);
		revision.setPeriodo(this);

		return revision;
	}

	public Revision removeRevision(Revision revision) {
		getRevisions().remove(revision);
		revision.setPeriodo(null);

		return revision;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	

}