package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the conducta_competencia database table.
 * 
 */
@Entity
@Table(name = "conducta_competencia")
public class ConductaCompetencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_conducta")
	private int id;

	@Column(name = "descripcion")
	private String descripcion;

	// bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name = "id_competencia")
	private Competencia competencia;

	// bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name = "id_dominio")
	private Dominio dominio;

	@Column(name = "orden")
	private int orden;
	
	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;
	
	@Column(name="hora_auditoria")
	private String horaAuditoria;
	
	@Column(name="usuario")
	private String usuario;

	public ConductaCompetencia() {
	}
	
	public ConductaCompetencia(int id, String descripcion,
			Competencia competencia, Dominio dominio, int orden,
			Timestamp fechaAuditoria, String horaAuditoria, String usuario) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.competencia = competencia;
		this.dominio = dominio;
		this.orden = orden;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuario = usuario;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int idConducta) {
		this.id = idConducta;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Competencia getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}

	public Dominio getDominio() {
		return dominio;
	}

	public void setDominio(Dominio dominio) {
		this.dominio = dominio;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

}