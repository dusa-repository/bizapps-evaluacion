package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the dominio database table.
 * 
 */
@Entity
@Table(name="dominio")
public class Dominio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_dominio")
	private int id;

	@Column(name="comentario")
	private String comentario;

	@Column(name="descripcion_dominio")
	private String descripcionDominio;

	@Column(name="tipo")
	private String tipo;

	@Column(name="usuario")
	private String usuario;
	
	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;
	
	@Column(name="hora_auditoria")
	private String horaAuditoria;
	
	public void setHoraAuditoria(String horaAuditoria) {
		this.horaAuditoria = horaAuditoria;
	}

	public Dominio() {
	}
	
	public Dominio(int idDominio, String comentario, String descripcionDominio,
			String tipo, String usuario, Timestamp fechaAuditoria,
			String horaAuditoria) {
		super();
		this.id = idDominio;
		this.comentario = comentario;
		this.descripcionDominio = descripcionDominio;
		this.tipo = tipo;
		this.usuario = usuario;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int idDominio) {
		this.id = idDominio;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getDescripcionDominio() {
		return this.descripcionDominio;
	}

	public void setDescripcionDominio(String descripcionDominio) {
		this.descripcionDominio = descripcionDominio;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	
	

}