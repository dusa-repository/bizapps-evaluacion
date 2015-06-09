package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


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
	
	@Column(name="peso")
	private double peso;
	
	@Column(name="id_dominio_relacionado")
	private int idDominioRelacionado;
	
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

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int getIdDominioRelacionado() {
		return idDominioRelacionado;
	}

	public void setIdDominioRelacionado(int idDominioRelacionado) {
		this.idDominioRelacionado = idDominioRelacionado;
	}

	
	
	

}