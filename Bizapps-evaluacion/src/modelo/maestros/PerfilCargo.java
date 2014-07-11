package modelo.maestros;

import java.io.Serializable;
import javax.persistence.*;

import modelo.seguridad.Usuario;

import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the perfil_cargo database table.
 * 
 */
@Entity
@Table(name = "perfil_cargo")
public class PerfilCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_perfil_cargo")
	private int id;
	
	// bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name = "id_cargo")
	private Cargo cargo;
	
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "nivel_academico")
	private String nivelAcademico;

	@Column(name = "especialidad")
	private String especialidad;

	@Column(name = "especializacion")
	private String especializacion;

	@Column(name = "experiencia_previa")
	private String experienciaPrevia;

	@Column(name = "idioma")
	private String idioma;

	@Column(name = "observaciones")
	private String observaciones;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria")
	private String horaAuditoria;

	@Column(name = "usuario")
	private String usuario;

	public PerfilCargo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PerfilCargo(int id, Cargo cargo, String descripcion,
			String nivelAcademico, String especialidad, String especializacion,
			String experienciaPrevia, String idioma, String observaciones,
			Timestamp fechaAuditoria, String horaAuditoria, String usuario) {
		super();
		this.id = id;
		this.cargo = cargo;
		this.descripcion = descripcion;
		this.nivelAcademico = nivelAcademico;
		this.especialidad = especialidad;
		this.especializacion = especializacion;
		this.experienciaPrevia = experienciaPrevia;
		this.idioma = idioma;
		this.observaciones = observaciones;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNivelAcademico() {
		return nivelAcademico;
	}

	public void setNivelAcademico(String nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getEspecializacion() {
		return especializacion;
	}

	public void setEspecializacion(String especializacion) {
		this.especializacion = especializacion;
	}

	public String getExperienciaPrevia() {
		return experienciaPrevia;
	}

	public void setExperienciaPrevia(String experienciaPrevia) {
		this.experienciaPrevia = experienciaPrevia;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

}