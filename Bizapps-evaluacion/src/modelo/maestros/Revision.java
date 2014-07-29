package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;


/**
 * The persistent class for the revision database table.
 * 
 */
@Entity
@Table(name="revision")
public class Revision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_revision")
	private int id;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="estado_revision")
	private String estadoRevision;
	
	@Column(name="mensaje_inicio",length = 1000)
	private String mensajeInicio;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;
	
	@Column(name="usuario")
	private String usuario;

	//bi-directional many-to-one association to Periodo
	@ManyToOne
	@JoinColumn(name="id_periodo")
	private Periodo periodo;


	public Revision() {
	}
	
	public Revision(int idRevision, String descripcion, String estadoRevision,
			Timestamp fechaAuditoria, String horaAuditoria, String usuario,
			Periodo periodo,String mensajeInicio) {
		super();
		this.id = idRevision;
		this.descripcion = descripcion;
		this.estadoRevision = estadoRevision;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuario = usuario;
		this.periodo = periodo;
		this.mensajeInicio= mensajeInicio;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int idRevision) {
		this.id = idRevision;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstadoRevision() {
		return this.estadoRevision;
	}

	public void setEstadoRevision(String estadoRevision) {
		this.estadoRevision = estadoRevision;
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

	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getMensajeInicio() {
		return mensajeInicio;
	}

	public void setMensajeInicio(String mensajeInicio) {
		this.mensajeInicio = mensajeInicio;
	}
	
	
	

}