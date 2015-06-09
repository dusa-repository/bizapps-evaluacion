package modelo.seguridad;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import modelo.maestros.Cargo;
import modelo.maestros.Competencia;
import modelo.maestros.Empresa;
import modelo.maestros.Gerencia;
import modelo.maestros.Periodo;
import modelo.maestros.Revision;
import modelo.maestros.UnidadOrganizativa;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private int idUsuario;
	
	@Column(name = "id_rol")
	private int idRol;

	private String apellido;

	private String cedula;

	private String email;

	@Column(name = "fecha_auditoria")
	private Timestamp fechaAuditoria;

	@Column(name = "hora_auditoria")
	private String horaAuditoria;

	private String login;

	private String nombre;

	private String password;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean estado;

	// bi-directional many-to-one association to Cargo
	@OneToMany(mappedBy = "usuario")
	private List<Cargo> cargos;

	// bi-directional many-to-one association to Competencia
	@OneToMany(mappedBy = "usuario")
	private List<Competencia> competencias;

	// bi-directional many-to-one association to Empresa
	@OneToMany(mappedBy = "usuario")
	private List<Empresa> empresas;

	// bi-directional many-to-one association to Gerencia
	@OneToMany(mappedBy = "usuario")
	private List<Gerencia> gerencias;

	// bi-directional many-to-one association to Periodo
	@OneToMany(mappedBy = "usuario")
	private List<Periodo> periodos;

	// bi-directional many-to-one association to Revision
	@OneToMany(mappedBy = "usuario")
	private List<Revision> revisions;

	// bi-directional many-to-one association to UnidadOrganizativa
	@OneToMany(mappedBy = "usuario")
	private List<UnidadOrganizativa> unidadOrganizativas;

	@Lob
	private byte[] imagen;

	public Usuario(int idUsuario, String apellido, String cedula, String email,
			Timestamp fechaAuditoria, String horaAuditoria, String login,
			String nombre, String password, boolean estado, byte[] imagen,
			String ficha) {
		super();
		this.idUsuario = idUsuario;
		this.apellido = apellido;
		this.cedula = cedula;
		this.email = email;
		this.fechaAuditoria = fechaAuditoria;
		this.horaAuditoria = horaAuditoria;
		this.login = login;
		this.nombre = nombre;
		this.password = password;
		this.estado = estado;
		this.imagen = imagen;
		this.ficha = ficha;
	}

	public Usuario() {
	}

	@Column(name = "ficha")
	private String ficha;

	public String getFicha() {
		return ficha;
	}

	public void setFicha(String ficha) {
		this.ficha = ficha;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
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

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public List<Cargo> getCargos() {
		return this.cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Cargo addCargo(Cargo cargo) {
		getCargos().add(cargo);

		return cargo;
	}

	public Cargo removeCargo(Cargo cargo) {
		getCargos().remove(cargo);
		return cargo;
	}

	public List<Competencia> getCompetencias() {
		return this.competencias;
	}

	public void setCompetencias(List<Competencia> competencias) {
		this.competencias = competencias;
	}

	public Competencia addCompetencia(Competencia competencia) {
		getCompetencias().add(competencia);

		return competencia;
	}

	public Competencia removeCompetencia(Competencia competencia) {
		getCompetencias().remove(competencia);

		return competencia;
	}

	public List<Empresa> getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa addEmpresa(Empresa empresa) {
		getEmpresas().add(empresa);

		return empresa;
	}

	public Empresa removeEmpresa(Empresa empresa) {
		getEmpresas().remove(empresa);

		return empresa;
	}

	public List<Gerencia> getGerencias() {
		return this.gerencias;
	}

	public void setGerencias(List<Gerencia> gerencias) {
		this.gerencias = gerencias;
	}

	public Gerencia addGerencia(Gerencia gerencia) {
		getGerencias().add(gerencia);

		return gerencia;
	}

	public Gerencia removeGerencia(Gerencia gerencia) {
		getGerencias().remove(gerencia);

		return gerencia;
	}

	public List<Periodo> getPeriodos() {
		return this.periodos;
	}

	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}

	public Periodo addPeriodo(Periodo periodo) {
		getPeriodos().add(periodo);

		return periodo;
	}

	public Periodo removePeriodo(Periodo periodo) {
		getPeriodos().remove(periodo);

		return periodo;
	}

	public List<Revision> getRevisions() {
		return this.revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}

	public Revision addRevision(Revision revision) {
		getRevisions().add(revision);

		return revision;
	}

	public Revision removeRevision(Revision revision) {
		getRevisions().remove(revision);

		return revision;
	}

	public List<UnidadOrganizativa> getUnidadOrganizativas() {
		return this.unidadOrganizativas;
	}

	public void setUnidadOrganizativas(
			List<UnidadOrganizativa> unidadOrganizativas) {
		this.unidadOrganizativas = unidadOrganizativas;
	}

	public UnidadOrganizativa addUnidadOrganizativa(
			UnidadOrganizativa unidadOrganizativa) {
		getUnidadOrganizativas().add(unidadOrganizativa);

		return unidadOrganizativa;
	}

	public UnidadOrganizativa removeUnidadOrganizativa(
			UnidadOrganizativa unidadOrganizativa) {
		getUnidadOrganizativas().remove(unidadOrganizativa);

		return unidadOrganizativa;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

}