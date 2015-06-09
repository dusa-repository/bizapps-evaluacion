package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the valoracion database table.
 * 
 */
@Entity
@Table(name="valoracion")
public class Valoracion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id_valoracion")
	private int id;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name="hora_auditoria")
	private String horaAuditoria;

	@Column(name="usuario")
	private String usuario;

	@Column(name="nombre")
	private String nombre;

	@Column(name="orden")
	private int orden;

	@Column(name="rango_inferior")
	private int rangoInferior;

	@Column(name="rango_superior")
	private int rangoSuperior;

	@Column(name="valor")
	private int valor;

	public Valoracion() {
	}
	
	
	public Valoracion(int id, String descripcion, Timestamp fechaAuditoria,
			String horaAuditoria, String usuario, String nombre, int orden,
			int rangoInferior, int rangoSuperior, int valor) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuario = usuario;
		this.nombre = nombre;
		this.orden = orden;
		this.rangoInferior = rangoInferior;
		this.rangoSuperior = rangoSuperior;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getRangoInferior() {
		return this.rangoInferior;
	}

	public void setRangoInferior(int rangoInferior) {
		this.rangoInferior = rangoInferior;
	}

	public int getRangoSuperior() {
		return this.rangoSuperior;
	}

	public void setRangoSuperior(int rangoSuperior) {
		this.rangoSuperior = rangoSuperior;
	}

	public int getValor() {
		return this.valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}