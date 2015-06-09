package modelo.maestros;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the cargo database table.
 * 
 */
@Entity
@Table(name = "cargo")
public class Cargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id_cargo")
	private int id;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria")
	private String horaAuditoria;

	@Column(name = "id_cargo_auxiliar")
	private String idCargoAuxiliar;

	@Column(name = "id_empresa_auxiliar")
	private String idEmpresaAuxiliar;

	@Column(name = "nomina")
	private String nomina;

	@Column(name = "usuario")
	private String usuario;

	@Column(name = "nivel_academico")
	private String nivelAcademico;

	@Column(name = "especialidad")
	private String especialidad;

	@Column(name = "especializacion")
	private String especializacion;

	@Column(name = "experiencia")
	private String experiencia;

	@Column(name = "segundo_idioma")
	private String idioma;

	@Column(name = "observaciones", length = 500)
	private String observaciones;


	public Cargo() {
	}

	public Cargo(int idCargo, String descripcion, Timestamp fechaAuditoria,
			String horaAuditoria, String idCargoAuxiliar,
			String idEmpresaAuxiliar, String nomina, String usuario) {
		super();
		this.id = idCargo;
		this.descripcion = descripcion;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.idCargoAuxiliar = idCargoAuxiliar;
		this.idEmpresaAuxiliar = idEmpresaAuxiliar;
		this.nomina = nomina;
		this.usuario = usuario;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int idCargo) {
		this.id = idCargo;
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

	public String getIdCargoAuxiliar() {
		return this.idCargoAuxiliar;
	}

	public void setIdCargoAuxiliar(String idCargoAuxiliar) {
		this.idCargoAuxiliar = idCargoAuxiliar;
	}

	public String getIdEmpresaAuxiliar() {
		return this.idEmpresaAuxiliar;
	}

	public void setIdEmpresaAuxiliar(String idEmpresaAuxiliar) {
		this.idEmpresaAuxiliar = idEmpresaAuxiliar;
	}

	public String getNomina() {
		return this.nomina;
	}

	public void setNomina(String nomina) {
		this.nomina = nomina;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	public String getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
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

}